package com.sages.avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecordBuilder;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GenericRecordBuilderWriterApp {

    public static void main(String[] args) throws IOException {

        String filePath = "avro/01-primitive.avsc";

        Schema schema = schema(filePath);

        GenericRecordBuilder dataBuilder = new GenericRecordBuilder(schema);
        dataBuilder.set("id", ThreadLocalRandom.current().nextLong());
        //dataBuilder.set("not-existing-field", "any value");
        dataBuilder.set("date", System.currentTimeMillis());

        GenericData.Record data = dataBuilder.build();

        GenericDatumWriter<GenericData.Record> datumWriter = new GenericDatumWriter<>();

        try (DataFileWriter<GenericData.Record> dataWriter = new DataFileWriter<>(datumWriter)) {
            File file = new File("day2/model/target/data.avro");
            dataWriter.create(data.getSchema(), file);
            dataWriter.append(data);

            System.out.println("Saved: " + data);
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
