package com.sages.avro;

import java.io.*;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.sages.schema.Transaction1;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class SpecificRecordBuilderApp {

    public static void main(String[] args) throws IOException {

        File file = new File("day2/model/target/data.avro");


        String filePath = "avro/01-primitive.avsc";
        final Schema schema = schema(filePath);


        write(file, schema);
        System.out.println("");
        read(file, schema);
    }

    private static void write(File toFile, Schema schema) throws IOException {
        Transaction1 data = Transaction1.newBuilder()
                .setId(ThreadLocalRandom.current().nextLong())
                .setDate(Instant.now())
                .build();

        SpecificDatumWriter<Transaction1> datumWriter = new SpecificDatumWriter<>(Transaction1.class);

        DataFileWriter<Transaction1> dataWriter = new DataFileWriter<>(datumWriter);


        dataWriter.create(data.getSchema(), toFile);
        //dataWriter.create(schema, toFile);
        dataWriter.append(data);
        System.out.println("Saved: " + data);
    }

    private static void read(File fromFile, Schema schema) throws IOException {
        SpecificDatumReader<Transaction1> datumReader = new SpecificDatumReader<>(Transaction1.class);

        DataFileReader<Transaction1> dataReader = new DataFileReader<>(fromFile, datumReader);

        System.out.println("Reading");
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
