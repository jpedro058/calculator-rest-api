package com.calculatorrestapi.rest.kafka;

import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        // Retrieve requestId from MDC
        String requestId = MDC.get("requestId");

        String enrichedMessage = String.format("requestId: %s, %s", requestId, message);

        kafkaTemplate.send(topic, enrichedMessage);
    }
}
