package com.calculatorrestapi.rest.controller;

import com.calculatorrestapi.calculator.KafkaConsumer;
import com.calculatorrestapi.rest.kafka.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
        // Generate or retrieve a requestId and set it in MDC
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);

        // Format the message for Kafka with the requestId
        String message = String.format("requestId: %s, operation: %s, a: %s, b: %s", requestId, operation, a, b);

        kafkaProducer.sendMessage("calculator-topic", message);

        // Format the response with the requestId
        String response = String.format("Message sent to Kafka topic: %s", message);

        // Log and return the response
        logger.info(response);

        // Clear MDC to avoid contamination of threads
        MDC.clear();

        return response;
    }
}
