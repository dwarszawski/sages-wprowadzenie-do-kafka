package com.sages.producer;

import com.sages.producer.dto.DObject;
import com.sages.producer.producer.DProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

// Remove manual serialization and set it up with application.yaml
//@SpringBootApplication
//@EnableScheduling
public class DProducerApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DProducerApp.class, args);
    }

    @Autowired
    private DProducer producer;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            var object = new DObject("emp-" + i, "Employee " + i, LocalDate.now());
            producer.send(object);
        }
    }

}