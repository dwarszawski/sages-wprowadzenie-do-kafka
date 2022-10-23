package com.sages.stream.app;

import com.sages.model.Transaction;
import com.sages.stream.extractors.RecordTimestampExtractor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

// Tumbling time window
@Configuration
public class ETransactionStream {

    @Bean
    public KStream<Long, Transaction> fStream(StreamsBuilder builder) {

        final JsonSerde transactionSerde = new JsonSerde<>(Transaction.class);
        final Serde<Long> longSerde = Serdes.Long();

        final Duration windowLength = Duration.ofSeconds(15);
        final Serde<Windowed<Long>> windowSerde = WindowedSerdes.timeWindowedSerdeFrom(Long.class, windowLength.toMillis());

        RecordTimestampExtractor timestampExtractor = new RecordTimestampExtractor();

        final KStream<Long, Transaction> transactionStream = builder.stream("transactions",
                Consumed.with(Serdes.Long(), transactionSerde, timestampExtractor, null));

        final KStream<Windowed<Long>, Long> suspiciousAccounts = transactionStream
                .groupByKey().windowedBy(TimeWindows.of(windowLength))
                .count()
                .filter((k, v)-> v > 2)
                .toStream();

        suspiciousAccounts
                .to("suspicious_accounts", Produced.with(windowSerde, longSerde));


        suspiciousAccounts.print(Printed.<Windowed<Long>, Long>toSysOut().withLabel("fraud detected"));

        builder.build();

        return null;
    }

}