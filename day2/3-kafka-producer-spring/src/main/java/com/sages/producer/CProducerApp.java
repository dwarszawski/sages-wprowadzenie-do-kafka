package com.sages.producer;

import com.sages.producer.producer.CProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Scaling consumers
//@SpringBootApplication
//@EnableScheduling
public class CProducerApp implements CommandLineRunner {

    @Autowired
    private CProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(CProducerApp.class, args);
    }

    // same key goes to same partition
    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10000; i++) {
            var key = "key-" + (i % 4);
            var data = "data " + i + " with key " + key;
            producer.send(key, data);

            Thread.sleep(500);
        }
    }

}