package com.sages.stream.app;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Simple kafka stream
// see config file
@Configuration
public class ATransactionStream {

    // spring takes care of stream lifycycle - starting and stopping stream
    @Bean
    public KStream<String, String> aStream(StreamsBuilder builder) {
        KStream<String, String> transactions = builder.stream("a_transactions",
                Consumed.with(Serdes.String(), Serdes.String()));

        KStream<String, String> outputStream = transactions.mapValues(s -> s.toUpperCase());
        outputStream.to("a_transactions_output");

        // useful for debugging, but don't do this on production
        transactions.print(Printed.<String, String>toSysOut().withLabel("source stream"));
        transactions.print(Printed.<String, String>toSysOut().withLabel("sink stream"));

        return outputStream;
    }

}

