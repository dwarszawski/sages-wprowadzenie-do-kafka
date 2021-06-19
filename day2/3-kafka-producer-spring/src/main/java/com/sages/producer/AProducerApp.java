package com.sages.producer;

import com.sages.producer.producer.AProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AProducerApp implements CommandLineRunner {

    @Autowired
    private AProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(AProducerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        producer.send("Bob");
    }

}
