package com.sages.producer;

import com.sages.producer.dto.DObject;
import com.sages.producer.producer.DProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

// Ability to rebalance consumers by changing the number of partitions
//@SpringBootApplication
//@EnableScheduling
public class EProducerApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EProducerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}