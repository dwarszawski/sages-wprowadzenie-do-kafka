package com.sages.producer.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sages.producer.dto.DObject;
import com.sages.producer.producer.FProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FScheduler {

    private static final Logger log = LoggerFactory.getLogger(FScheduler.class);

    @Autowired
    private FProducer producer;

    //@Scheduled(fixedRate = 5000)
    public void generateCarLocation() throws JsonProcessingException {

        DObject first = new DObject("first", "my description", LocalDate.now());
        DObject second = new DObject("second", "my description", LocalDate.now());
        DObject third = new DObject("third", "my description", LocalDate.now());

        first.setDate(LocalDate.now().minusWeeks(1));
        second.setDate(LocalDate.now().minusWeeks(1));
        third.setDate(LocalDate.now().minusWeeks(1));


        producer.send(first);
        producer.send(second);
        producer.send(third);

        log.info("Done");
    }


}
