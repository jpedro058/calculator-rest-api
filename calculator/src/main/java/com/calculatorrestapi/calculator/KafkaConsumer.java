package com.calculatorrestapi.calculator;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "calculator-topic", groupId = "calculator-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);

        // Parse the message to extract the operation and operands
        String[] parts = message.split(", ");
        String operation = parts[0].split(": ")[1];
        double a = Double.parseDouble(parts[1].split(": ")[1]);
        double b = Double.parseDouble(parts[2].split(": ")[1]);

        switch (operation) {
            case "add":
                System.out.println("Result: " + (a + b));
                break;
            case "subtract":
                System.out.println("Result: " + (a - b));
                break;
            case "multiply":
                System.out.println("Result: " + (a * b));
                break;
            case "divide":
                if (b != 0) {
                    System.out.println("Result: " + (a / b));
                } else {
                    System.out.println("Error: Cannot divide by zero");
                }
                break;
            default:
                System.out.println("Unknown operation");
        }
    }
}
