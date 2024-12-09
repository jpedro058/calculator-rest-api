package com.calculatorrestapi.rest.controller;

import com.calculatorrestapi.calculator.KafkaConsumer;
import com.calculatorrestapi.rest.kafka.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private final KafkaProducer kafkaProducer;
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);


    @Autowired
    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/send")
    public String sendMessage(
            @RequestParam String operation,
            @RequestParam String a,
            @RequestParam String b) {
        // Format the message for Kafka
        String message = String.format("operation: %s, a: %s, b: %s", operation, a, b);
        kafkaProducer.sendMessage("calculator-topic", message);
        logger.info("Message sent to Kafka topic: {}", message);
        return "Message sent to Kafka topic: " + message;
    }
}
