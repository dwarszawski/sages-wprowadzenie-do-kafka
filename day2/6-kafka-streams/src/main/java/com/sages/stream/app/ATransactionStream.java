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

@Configuration
public class ATransactionStream {

    @Bean
    public KStream<Long, String> aStream(StreamsBuilder builder) {

        JsonSerde transactionSerde = new JsonSerde<>(Transaction.class);

        KStream<Long, Transaction> transactions = builder.stream("transactions",
                Consumed.with(Serdes.Long(), transactionSerde));

        final KStream<Long, Transaction> filteredTransactions = transactions.filter((k, v) ->
                v.getDescription().equalsIgnoreCase("DEBIT") ||
                        v.getDescription().equalsIgnoreCase("CREDIT"));

        KStream<Long, String> outputStream = filteredTransactions.mapValues(s -> s.getDescription().toLowerCase());

        outputStream.to("transaction_descriptions", Produced.with(Serdes.Long(), Serdes.String()));

        // useful for debugging, but don't do this on production
        transactions.print(Printed.<Long, Transaction>toSysOut().withLabel("source stream"));
        outputStream.print(Printed.<Long, String>toSysOut().withLabel("sink stream"));

        return outputStream;
    }

}

