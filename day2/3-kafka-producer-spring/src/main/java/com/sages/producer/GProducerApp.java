package com.sages.producer;

import com.sages.producer.dto.DObject;
import com.sages.producer.producer.GProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

// Handling exceptions on consumer side
// Ability to filter messages with overriding kafka listener container factory
//@SpringBootApplication
//@EnableScheduling
public class GProducerApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GProducerApp.class, args);
    }

    @Autowired
    private GProducer producer;

    @Override
    public void run(String... args) throws Exception {
        DObject first = new DObject("first", "my description", LocalDate.now());
        DObject second = new DObject("second", "my description", LocalDate.now());
        DObject third = new DObject("third", "my description", LocalDate.now());

        producer.send(first);
        producer.send(second);
        producer.send(third);
    }

}