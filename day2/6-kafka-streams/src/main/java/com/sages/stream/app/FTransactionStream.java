package com.sages.stream.app;

import com.sages.stream.dto.CTransaction;
import com.sages.stream.extractors.RecordTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

// Tumbling time window
//@Configuration
public class FTransactionStream {

    @Bean
    public KStream<String, CTransaction> fStream(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var transactionSerde = new JsonSerde<>(CTransaction.class);
        var doubleSerde = Serdes.Double();

        var windowLength = Duration.ofHours(1);
        var windowSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class, windowLength.toMillis());

        var timestampExtractor = new RecordTimestampExtractor();

        var transactionStream = builder.stream("f_transactions",
                Consumed.with(stringSerde, transactionSerde, timestampExtractor, null));

        transactionStream
                .mapValues((k, v) -> v.getType().equalsIgnoreCase("DEBIT") ? v.getAmount() : -1 * v.getAmount())
                .groupByKey().windowedBy(TimeWindows.of(windowLength))
                .reduce(Double::sum, Materialized.with(stringSerde, doubleSerde)).toStream()
                .through("f_transactions_windowed", Produced.with(windowSerde, doubleSerde))
                .print(Printed.toSysOut());

        return transactionStream;
    }

}