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

// Aggregating to balance stream with reduce method
//@Configuration
public class DTransactionStream {

    @Bean
    public KStream<String, CTransaction> dStream(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var transactionSerde = new JsonSerde<>(CTransaction.class);
        var doubleSerde = Serdes.Double();

        var balanceStream = builder.stream("d_transactions", Consumed.with(stringSerde, transactionSerde));

        balanceStream
                .selectKey((k, v) -> v.getAccountId())
                .mapValues((k, v) -> v.getType().equalsIgnoreCase("DEBIT") ? v.getAmount() : (-1) * v.getAmount())
                .groupByKey()
                // reduce can be used in place of aggregate as the output type is the same
                .reduce(Double::sum, Materialized.with(stringSerde, doubleSerde))
                .toStream().to("d_balances", Produced.with(stringSerde, doubleSerde));

        return balanceStream;
    }
}
