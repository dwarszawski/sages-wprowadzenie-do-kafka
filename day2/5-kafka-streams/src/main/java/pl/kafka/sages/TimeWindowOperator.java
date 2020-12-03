package pl.kafka.sages;

import pl.kafka.sages.utils.PriorityQueueSerde;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.kstream.WindowedSerdes;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Properties;

/**
 *
 * Określanie najbardziej popularnych artykułów dla danej branży na podstawie strumienia danych o wyświetleniach stron
 */
public class TimeWindowOperator {

  static final String TOP_INSDUSTRY_NEWS = "top-industry-news";
  static final String PAGE_VIEWS = "page-views";

  private static boolean isArticle(final GenericRecord record) {
    final Utf8 flags = (Utf8) record.get("flags");
    if (flags == null) {
      return false;
    }
    return flags.toString().contains("ARTICLE");
  }

  public static void main(final String[] args) throws Exception {
    final String bootstrapServers = args.length > 0 ? args[0] : "localhost:9092";
    final String schemaRegistryUrl = args.length > 1 ? args[1] : "http://localhost:8081";
    final KafkaStreams streams = buildTopArticlesStream(
        bootstrapServers,
        schemaRegistryUrl,
      "/tmp/kafka-streams");


    streams.cleanUp();
    streams.start();

    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
  }

  static KafkaStreams buildTopArticlesStream(final String bootstrapServers,
                                             final String schemaRegistryUrl,
                                             final String stateDir) throws IOException {
    final Properties streamsConfiguration = new Properties();

    streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "top-articles-app");
    streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "top-articles-client");
    streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    // Where to find the Confluent schema registry instance(s)
    streamsConfiguration.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
    streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
    streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, stateDir);
    streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);

    final Serde<String> stringSerde = Serdes.String();

    final Map<String, String> serdeConfig = Collections.singletonMap(
        AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

    final Serde<GenericRecord> keyAvroSerde = new GenericAvroSerde();
    keyAvroSerde.configure(serdeConfig, true);

    final Serde<GenericRecord> valueAvroSerde = new GenericAvroSerde();
    valueAvroSerde.configure(serdeConfig, false);

    final Serde<Windowed<String>> windowedStringSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class);

    final StreamsBuilder builder = new StreamsBuilder();

    final KStream<byte[], GenericRecord> views = builder.stream(PAGE_VIEWS);

    final InputStream statsSchema = TimeWindowOperator.class.getClassLoader()
        .getResourceAsStream("avro/pageviewstats.avsc");
    final Schema schema = new Schema.Parser().parse(statsSchema);

    final KStream<GenericRecord, GenericRecord> articleViews = views
      // filter only article pages
      .filter((dummy, record) -> isArticle(record))
      // map <page id, industry> as key by making user the same for each record
      .map((dummy, article) -> {
        final GenericRecord clone = new GenericData.Record(article.getSchema());
        clone.put("user", "user");
        clone.put("page", article.get("page"));
        clone.put("industry", article.get("industry"));
        return new KeyValue<>(clone, clone);
      });

    final KTable<Windowed<GenericRecord>, Long> viewCounts = articleViews
      // count the clicks per hour, using tumbling windows with a size of one hour
      .groupByKey(Grouped.with(keyAvroSerde, valueAvroSerde))
      .windowedBy(TimeWindows.of(Duration.ofHours(1)))
      .count();

    final Comparator<GenericRecord> comparator =
      (o1, o2) -> (int) ((Long) o2.get("count") - (Long) o1.get("count"));

    final KTable<Windowed<String>, PriorityQueue<GenericRecord>> allViewCounts = viewCounts
      .groupBy(
        // the selector
        (windowedArticle, count) -> {
          // project on the industry field for key
          final Windowed<String> windowedIndustry =
            new Windowed<>(windowedArticle.key().get("industry").toString(),
              windowedArticle.window());
          // add the page into the value
          final GenericRecord viewStats = new GenericData.Record(schema);
          viewStats.put("page", windowedArticle.key().get("page"));
          viewStats.put("user", "user");
          viewStats.put("industry", windowedArticle.key().get("industry"));
          viewStats.put("count", count);
          return new KeyValue<>(windowedIndustry, viewStats);
        },
        Grouped.with(windowedStringSerde, valueAvroSerde)
      ).aggregate(
        // the initializer
        () -> new PriorityQueue<>(comparator),

        // the "add" aggregator
        (windowedIndustry, record, queue) -> {
          queue.add(record);
          return queue;
        },

        // the "remove" aggregator
        (windowedIndustry, record, queue) -> {
          queue.remove(record);
          return queue;
        },

        Materialized.with(windowedStringSerde, new PriorityQueueSerde<>(comparator, valueAvroSerde))
        );

    final int topN = 100;
    final KTable<Windowed<String>, String> topViewCounts = allViewCounts
      .mapValues(queue -> {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < topN; i++) {
          final GenericRecord record = queue.poll();
          if (record == null) {
            break;
          }
          sb.append(record.get("page").toString());
          sb.append("\n");
        }
        return sb.toString();
      });

    topViewCounts.toStream().to(TOP_INSDUSTRY_NEWS, Produced.with(windowedStringSerde, stringSerde));
    return new KafkaStreams(builder.build(), streamsConfiguration);
  }

}
