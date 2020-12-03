package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class ItemProducer {

    @Autowired
    private KafkaTemplate<String, Item> kafkaTemplate;


    @GetMapping("/topic/{topic}/{event}")
    public void produce(@PathVariable("topic") final String topic, @PathVariable("event") final String item){
        kafkaTemplate.send(topic, new Item(item, "", 1.23f));

        System.out.println("produced" + item);
    }
}
