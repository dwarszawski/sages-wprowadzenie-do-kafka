package com.sages.stream.app;

import com.sages.stream.dto.CTransaction;
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
public class CTransactionStream {

    @Bean
    public KStream<String, CTransaction> cStream(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var transactionSerde = new JsonSerde<>(CTransaction.class);
        var doubleSerde = Serdes.Double();

        var balanceStream = builder.stream("c_transactions", Consumed.with(stringSerde, transactionSerde));

        balanceStream
                .selectKey((k, v) -> v.getAccountId())
                .mapValues((k, v) -> v.getAmount())
                .groupByKey()
                // initializer is only called when no record exists for given key
                .aggregate(
                        () -> 0.0, (aggKey, newValue, aggValue) -> aggValue + newValue,
                        Materialized.with(stringSerde, doubleSerde))
                .toStream().to("c_balances", Produced.with(stringSerde, doubleSerde));

        return balanceStream;
    }
}
