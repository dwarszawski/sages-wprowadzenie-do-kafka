package com.sages.stream.app;

import com.sages.model.Transaction;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

// Simple kafka stream
// see config file
@Configuration
public class ATransactionStream {

    // spring takes care of stream lifycycle - starting and stopping stream
    @Bean
    public KStream<Long, String> aStream(StreamsBuilder builder) {

        var transactionSerde = new JsonSerde<>(Transaction.class);

        KStream<Long, Transaction> transactions = builder.stream("transactions",
                Consumed.with(Serdes.Long(), transactionSerde));

        KStream<Long, String> outputStream = transactions.mapValues(s -> s.getDescription().toLowerCase());
        outputStream.to("transaction_descriptions", Produced.with(Serdes.Long(), Serdes.String()));

        // useful for debugging, but don't do this on production
        transactions.print(Printed.<Long, Transaction>toSysOut().withLabel("source stream"));
        outputStream.print(Printed.<Long, String>toSysOut().withLabel("sink stream"));

        return outputStream;
    }

}

