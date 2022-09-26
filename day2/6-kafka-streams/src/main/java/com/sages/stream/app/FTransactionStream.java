package com.sages.stream.app;

import com.sages.model.Transaction;
import com.sages.stream.extractors.RecordTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

// Tumbling time window
@Configuration
public class FTransactionStream {

//    @Bean
//    public KStream<Long, Transaction> fStream(StreamsBuilder builder) {
//
//        var transactionSerde = new JsonSerde<>(Transaction.class);
//        var doubleSerde = Serdes.Double();
//
//        var windowLength = Duration.ofSeconds(15);
//        var windowSerde = WindowedSerdes.timeWindowedSerdeFrom(Long.class, windowLength.toMillis());
//
//        var timestampExtractor = new RecordTimestampExtractor();
//
//        var transactionStream = builder.stream("transactions",
//                Consumed.with(Serdes.Long(), transactionSerde, timestampExtractor, null));
//
//        transactionStream
//                .mapValues((k, v) -> v.getDescription().equalsIgnoreCase("DEBIT") ? v.getValue() : (-1) * v.getValue())
//                .groupByKey().windowedBy(TimeWindows.of(windowLength))
//                .reduce(Double::sum, Materialized.with(Serdes.Long(), doubleSerde)).toStream()
//                .through("transactions_groups", Produced.with(windowSerde, doubleSerde))
//                .print(Printed.toSysOut());
//
//        return transactionStream;
//    }

}