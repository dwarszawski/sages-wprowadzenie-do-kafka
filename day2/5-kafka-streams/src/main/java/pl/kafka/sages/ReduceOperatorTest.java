package pl.kafka.sages;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.stream.IntStream;

public class ReduceOperatorTest {

  public static void main(final String[] args) {
    final String bootstrapServers = "localhost:9092";
    produceInput(bootstrapServers);
    consumeOutput(bootstrapServers);
  }

  private static void consumeOutput(final String bootstrapServers) {
    final Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "reduce-operator-consumer");
    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    final KafkaConsumer<Integer, Integer> consumer = new KafkaConsumer<>(properties);
    consumer.subscribe(Collections.singleton(ReduceOperator.SUM_TOPIC));
    while (true) {
      final ConsumerRecords<Integer, Integer> records =
              consumer.poll(Duration.ofMillis(Long.MAX_VALUE));

      for (final ConsumerRecord<Integer, Integer> record : records) {
        System.out.println("Current sum of odd numbers is:" + record.value());
      }
    }
  }

  private static void produceInput(final String bootstrapServers) {
    final Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);

    final KafkaProducer<Integer, Integer> producer = new KafkaProducer<>(props);

    IntStream.range(0, 100)
            .mapToObj(val -> new ProducerRecord<>(ReduceOperator.INPUT_NUMBERS_TOPIC, val, val))
            .forEach(producer::send);

    producer.flush();
  }


}
