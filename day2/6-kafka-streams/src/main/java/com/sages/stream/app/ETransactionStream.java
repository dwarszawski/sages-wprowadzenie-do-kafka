package com.sages.stream.app;

import com.sages.model.Transaction;
import com.sages.stream.extractors.RecordTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

// Extracting custom timestamp from record
// See RecordTimestampExtractor
//@Configuration
public class ETransactionStream {

    @Bean
    public KStream<Long, Transaction> eStream(StreamsBuilder builder) {
        JsonSerde jsonSerde = new JsonSerde<>(Transaction.class);
        RecordTimestampExtractor timestampExtractor = new RecordTimestampExtractor();

        // null for reset policy (auto offset reset) - to enforce default configuration
        KStream<Long, Transaction> transactions = builder.stream("transactions",
                Consumed.with(Serdes.Long(), jsonSerde, timestampExtractor, null));

        transactions.to("transactions_timestamp", Produced.with(Serdes.Long(), jsonSerde));
        // check if produce same value as given in the message payload
        return transactions;
    }

}
