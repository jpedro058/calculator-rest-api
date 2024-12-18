package com.calculatorrestapi.rest.controller;

import com.calculatorrestapi.calculator.CalculatorService;
import com.calculatorrestapi.calculator.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    CalculatorService calculatorService;

    // Helper method to format the response
    private ResponseEntity<Map<String, BigDecimal>> createResponse(BigDecimal result) {
        Map<String, BigDecimal> response = new HashMap<>();
        logger.info("Operation result: {}", result);
        response.put("result", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sum")
    public ResponseEntity<Map<String, BigDecimal>> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        BigDecimal result = calculatorService.add(a, b);
        return createResponse(result);
    }

    @GetMapping("/subtract")
    public ResponseEntity<Map<String, BigDecimal>> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        BigDecimal result = calculatorService.subtract(a, b);
        return createResponse(result);
    }

    @GetMapping("/divide")
    public ResponseEntity<?> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        try {
            BigDecimal result = calculatorService.divide(a, b);
            Map<String, BigDecimal> successResponse = new HashMap<>();
            logger.info("Operation result: {}", result);
            successResponse.put("result", result);
            return ResponseEntity.ok(successResponse);
        } catch (ArithmeticException e) {
            Map<String, String> errorResponse = new HashMap<>();
            logger.error("Error: Division by zero: {} / {}", a, b);
            errorResponse.put("error", "Cannot divide by zero");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/multiply")
    public ResponseEntity<Map<String, BigDecimal>> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        BigDecimal result = calculatorService.multiply(a, b);
        return createResponse(result);
    }
}
