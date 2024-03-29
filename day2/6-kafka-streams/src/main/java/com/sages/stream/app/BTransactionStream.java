package com.sages.stream.app;

import com.sages.model.Transaction;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

// Aggregatoting to balance stream
//@Configuration
public class BTransactionStream {

    @Bean
    public KStream<Long, Transaction> cStream(StreamsBuilder builder) {
        JsonSerde transactionSerde = new JsonSerde<>(Transaction.class);
        final Serde<Double> doubleSerde = Serdes.Double();

        KStream<Long, Transaction> balanceStream = builder.stream("transactions", Consumed.with(Serdes.Long(), transactionSerde));

        balanceStream
                .selectKey((k, v) -> v.getAccountId())
                .mapValues((k, v) -> v.getDescription().equalsIgnoreCase("DEBIT") ? v.getValue() : (-1) * v.getValue())
                .groupByKey()
                // initializer is only called when no record exists for given key
                .aggregate(
                        () -> 0.0, (aggKey, newValue, aggValue) -> aggValue + newValue,
                        Materialized.with(Serdes.Long(), doubleSerde))
                .toStream().to("balances", Produced.with(Serdes.Long(), doubleSerde));

        return balanceStream;
    }
}
