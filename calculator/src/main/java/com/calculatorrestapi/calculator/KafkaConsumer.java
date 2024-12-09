package com.calculatorrestapi.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "calculator-topic", groupId = "calculator-group")
    public void listen(String message) {
        logger.info("Received message: {}", message);

        try {
            String[] parts = message.split(", ");
            String operation = parts[0].split(": ")[1];
            double a = Double.parseDouble(parts[1].split(": ")[1]);
            double b = Double.parseDouble(parts[2].split(": ")[1]);

            double result;
            switch (operation) {
                case "add":
                    result = a + b;
                    break;
                case "subtract":
                    result = a - b;
                    break;
                case "multiply":
                    result = a * b;
                    break;
                case "divide":
                    if (b == 0) {
                        logger.error("Cannot by zero: {} / {}", a, b);
                        return;
                    }
                    result = a / b;
                    break;
                default:
                    logger.warn("Unknown operation: {}", operation);
                    return;
            }
            logger.info("Operation result: {}", result);
        } catch (Exception e) {
            logger.error("Error processing message: {}", message, e);
        }
    }
}
