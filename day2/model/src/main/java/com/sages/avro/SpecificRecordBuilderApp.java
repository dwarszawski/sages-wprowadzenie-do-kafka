package com.sages.avro;

import com.sages.schema.evolution.backward.TransactionV1;
import com.sages.schema.evolution.backward.TransactionV2;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class SpecificRecordBuilderApp {

    public static void main(String[] args) throws IOException {

        File file = new File("day2/model/target/data.avro");

        String filePath = "avro/11-transactionV1.avsc";
        final Schema schema = schema(filePath);


        write(file, schema);
        System.out.println();
        read(file, schema);
    }

    private static void write(File toFile, Schema schema) throws IOException {
        TransactionV1 data = TransactionV1.newBuilder()
                .setTransactionId(ThreadLocalRandom.current().nextLong())
                .setTransactionValue(1230.12)
                .build();

        SpecificDatumWriter<TransactionV1> datumWriter = new SpecificDatumWriter<>(TransactionV1.class);

        DataFileWriter<TransactionV1> dataWriter = new DataFileWriter<>(datumWriter);


        dataWriter.create(data.getSchema(), toFile);
        //dataWriter.create(schema, toFile);
        dataWriter.append(data);
        System.out.println("Saved: " + data);
        dataWriter.close();
    }

    private static void read(File fromFile, Schema schema) throws IOException {
//        SpecificDatumReader<TransactionV1> datumReader = new SpecificDatumReader<>(TransactionV1.class);
        SpecificDatumReader<TransactionV2> datumReader = new SpecificDatumReader<>(TransactionV2.class);

//        DataFileReader<TransactionV1> dataReader = new DataFileReader<>(fromFile, datumReader);
        DataFileReader<TransactionV2> dataReader = new DataFileReader<>(fromFile, datumReader);
        System.out.println("Reading...");
        dataReader.forEach(System.out::println);

    }

    private static Schema schema(String filePath) throws IOException {
        final String schemaString = getResourceFileAsString(filePath);
        return new Schema.Parser().parse(schemaString);
    }

    private static String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}
