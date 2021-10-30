package com.sages.avro;

import java.io.*;
import java.util.stream.Collectors;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;

public class GenericRecordBuilderReaderApp {

    public static void main(String[] args) throws IOException {
        File file = new File("day2/model/target/data.avro");

        String schemaPath = "avro/01-primitive.avsc";

        Schema schema = schema(schemaPath);

        GenericDatumReader datumReader = new GenericDatumReader<GenericRecord>(schema);

        try (DataFileReader<GenericRecord> dataReader = new DataFileReader<>(file, datumReader)) {
            dataReader.forEach(data -> {
                System.out.println(data);
                long transactionId = (long) data.get("id");
                System.out.println(transactionId);
            });
        }
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
