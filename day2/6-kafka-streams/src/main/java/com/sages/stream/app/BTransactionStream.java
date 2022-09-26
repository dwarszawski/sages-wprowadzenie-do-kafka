package com.sages.stream.app;

import com.sages.model.Transaction;
import com.sages.stream.dto.EnrichedTransaction;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;


// Generating enriched transactions
//@Configuration
public class BTransactionStream {

    @Bean
    public KStream<Long, Transaction> bStream(StreamsBuilder builder) {

        JsonSerde transactionJsonSerde = new JsonSerde<>(Transaction.class);
        JsonSerde enrichedTransactionJsonSerde = new JsonSerde<>(EnrichedTransaction.class);

        KStream<Long, Transaction> sourceStream = builder.stream("transactions",
                Consumed.with(Serdes.Long(), transactionJsonSerde));
        KStream<Long, EnrichedTransaction> enrichedStream = sourceStream.mapValues(this::enrichTransaction);

        enrichedStream.to("enriched_transactions", Produced.with(Serdes.Long(), enrichedTransactionJsonSerde));

        sourceStream.print(Printed.<Long, Transaction>toSysOut().withLabel("Transaction stream"));
        enrichedStream.print(Printed.<Long, EnrichedTransaction>toSysOut().withLabel("Enriched transaction stream"));

        return sourceStream;
    }

    private EnrichedTransaction enrichTransaction(Transaction transaction) {

        return new EnrichedTransaction(
                Long.toString(transaction.getId()),
                transaction.getDescription().equalsIgnoreCase("DEBIT") ? transaction.getValue() : (-1) * transaction.getValue(),
                transaction.getDate()
        );
    }

}
