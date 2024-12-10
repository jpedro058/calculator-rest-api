package com.calculatorrestapi.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "calculator-topic", groupId = "calculator-group")
    public void listen(String message) {
        String requestId = null;
        try {
            // Parse the message and extract the requestId
            String[] parts = message.split(", ");
            for (String part : parts) {
                if (part.startsWith("requestId:")) {
                    requestId = part.split(": ")[1];
                    MDC.put("requestId", requestId); // Add requestId to MDC
                    break;
                }
            }

            // Log the received message with requestId
            logger.info("Received message with requestId: {}, content: {}", requestId, message);

            String operation = null;
            BigDecimal a = null;
            BigDecimal b = null;

            // Loop through parts and extract the relevant values
            for (String part : parts) {
                if (part.startsWith("operation:")) {
                    operation = part.split(": ")[1];
                } else if (part.startsWith("a:")) {
                    a = new BigDecimal(part.split(": ")[1]);
                } else if (part.startsWith("b:")) {
                    b = new BigDecimal(part.split(": ")[1]);
                }
            }

            // Ensure operation and operands are not null
            if (operation == null || a == null || b == null) {
                logger.error("Missing values in message: {}", message);
                return;
            }

            // Perform the operation
            BigDecimal result;
            switch (operation) {
                case "add":
                    result = a.add(b);
                    break;
                case "subtract":
                    result = a.subtract(b);
                    break;
                case "multiply":
                    result = a.multiply(b);
                    break;
                case "divide":
                    if (b.compareTo(BigDecimal.ZERO) == 0) {
                        logger.error("Cannot divide by zero: {} / {}", a, b);
                        return;
                    }
                    result = a.divide(b, 10, BigDecimal.ROUND_HALF_UP); // Use proper rounding for division
                    break;
                default:
                    logger.warn("Unknown operation: {}", operation);
                    return;
            }

            // Log the operation result
            logger.info("Operation result with requestId {}: {}", requestId, result);
        } catch (Exception e) {
            logger.error("Error processing message: {}", message, e);
        } finally {
            // Clean up MDC to prevent leakage between requests
            MDC.remove("requestId");
        }
    }
}
