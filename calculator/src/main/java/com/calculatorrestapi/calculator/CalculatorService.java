package com.calculatorrestapi.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    public BigDecimal add(BigDecimal a, BigDecimal b) {
        logger.info("Performing addition: {} + {}", a, b);
        return a.add(b);
    }

    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        logger.info("Performing subtraction: {} - {}", a, b);
        return a.subtract(b);
    }

    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        logger.info("Performing multiplication: {} * {}", a, b);
        return a.multiply(b);
    }

    public BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (b.equals(BigDecimal.ZERO)) {
            logger.error("Division by zero: {} / {}", a, b);
            throw new ArithmeticException("Error: Cannot divide by zero");
        }
        logger.info("Performing division: {} / {}", a, b);

        // Set scale and rounding mode for non-terminating decimal expansion
        return a.divide(b, 10, RoundingMode.HALF_UP);  // Precision of 10 decimal places with rounding mode
    }
}
