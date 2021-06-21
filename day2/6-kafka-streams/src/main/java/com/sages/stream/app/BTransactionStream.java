package com.sages.stream.app;

import com.sages.stream.dto.BTransaction;
import com.sages.stream.dto.ATransaction;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;


// Generating enriched transactions
//@Configuration
public class BTransactionStream {

    @Bean
    public KStream<String, ATransaction> bStream(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var transactionJsonSerde = new JsonSerde<>(ATransaction.class);
        var enrichedTransactionJsonSerde = new JsonSerde<>(BTransaction.class);

        KStream<String, ATransaction> sourceStream = builder.stream("b_transactions",
                Consumed.with(stringSerde, transactionJsonSerde));
        KStream<String, BTransaction> enrichedStream = sourceStream.mapValues(this::enrichTransaction);

        enrichedStream.to("b_enriched_transactions", Produced.with(stringSerde, enrichedTransactionJsonSerde));

        sourceStream.print(Printed.<String, ATransaction>toSysOut().withLabel("Transaction stream"));
        enrichedStream.print(Printed.<String, BTransaction>toSysOut().withLabel("Enriched transaction stream"));

        return sourceStream;
    }

    private BTransaction enrichTransaction(ATransaction transaction) {
        String type = transaction.getValue() < 0 ? "WITHDRAW" : "DEPOSIT";
        return new BTransaction(transaction, type);
    }

}
