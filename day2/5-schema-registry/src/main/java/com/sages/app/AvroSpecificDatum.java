package com.sages.app;

import com.sages.schema.Transaction1;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;


public class AvroSpecificDatum {

    public static void main(String[] args) {
        System.out.println("With default");
        File fileDefault = new File("Transaction1Default.avro");
        writeDefault(fileDefault);
        System.out.println("");
        read(fileDefault);

        System.out.println("");
        System.out.println("Without default");
        File file = new File("Transaction1.avro");
        write(file);
        System.out.println("");
        read(file);
    }

    private static void writeDefault(File toFile) {
        Transaction1 data = Transaction1.newBuilder().setTitle("tax fee").build();
        SpecificDatumWriter datumWriter = new SpecificDatumWriter<>(Transaction1.class);

        try (DataFileWriter dataWriter = new DataFileWriter<>(datumWriter)) {
            dataWriter.create(data.getSchema(), toFile);
            dataWriter.append(data);

            System.out.println("Writing");
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void write(File toFile) {
        Transaction1 data = Transaction1.newBuilder().setTitle("tax fee").setStatus("ACTIVE").setIsDebit(false)
                .build();
        SpecificDatumWriter datumWriter = new SpecificDatumWriter<>(Transaction1.class);

        try (DataFileWriter dataWriter = new DataFileWriter<>(datumWriter)) {
            dataWriter.create(data.getSchema(), toFile);
            dataWriter.append(data);

            System.out.println("Writing");
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void read(File fromFile) {
        SpecificDatumReader datumReader = new SpecificDatumReader<>(Transaction1.class);

        System.out.println("Reading");
        try (DataFileReader dataReader = new DataFileReader<>(fromFile, datumReader)) {
            dataReader.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
