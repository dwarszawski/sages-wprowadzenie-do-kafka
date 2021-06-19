package com.sages.stream.app;

import com.sages.stream.dto.CTransaction;
import com.sages.stream.extractors.RecordTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class ETransactionStream {

    @Bean
    public KStream<String, CTransaction> eStream(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var jsonSerde = new JsonSerde<>(CTransaction.class);
        var timestampExtractor = new RecordTimestampExtractor();

        // null for reset policy (auto offset reset) - to enforce default configuration
        var transactions = builder.stream("e_transactions",
                Consumed.with(stringSerde, jsonSerde, timestampExtractor, null));

        transactions.to("e_transactions_timestamp", Produced.with(stringSerde, jsonSerde));
        // check if produce same value as given in the message payload
        return transactions;
    }

}
