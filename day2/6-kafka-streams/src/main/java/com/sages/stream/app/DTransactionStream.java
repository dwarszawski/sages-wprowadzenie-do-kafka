package com.sages.stream.app;


import com.sages.model.Transaction;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

// Aggregating to balance stream with reduce method
//@Configuration
public class DTransactionStream {

    @Bean
    public KStream<Long, Transaction> dStream(StreamsBuilder builder) {
        var transactionSerde = new JsonSerde<>(Transaction.class);

        var balanceStream = builder.stream("transactions", Consumed.with(Serdes.Long(), transactionSerde));

        balanceStream
                .selectKey((k, v) -> v.getId())
                .mapValues((k, v) -> v.getDescription().equalsIgnoreCase("DEBIT") ? v.getValue() : (-1) * v.getValue())
                .groupByKey()
                // reduce can be used in place of aggregate as the output type is the same
                .reduce(Double::sum, Materialized.with(Serdes.Long(), Serdes.Double()))
                .toStream().to("balances", Produced.with(Serdes.Long(), Serdes.Double()));

        return balanceStream;
    }
}
