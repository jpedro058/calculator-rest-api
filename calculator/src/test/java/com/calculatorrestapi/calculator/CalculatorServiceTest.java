package com.calculatorrestapi.calculator;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest(classes = CalculatorApiApplication.class)
public class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    public void testAdd() {
        BigDecimal result = calculatorService.add(new BigDecimal("1"), new BigDecimal("2"));
        assertEquals("1 + 2 should equal 3", new BigDecimal("3"), result);
    }

    @Test
    public void testSubtract() {
        BigDecimal result = calculatorService.subtract(new BigDecimal("5"), new BigDecimal("3"));
        assertEquals("5 - 3 should equal 2", new BigDecimal("2"), result);
    }

    @Test
    public void testMultiply() {
        BigDecimal result = calculatorService.multiply(new BigDecimal("2"), new BigDecimal("3"));
        assertEquals("2 * 3 should equal 6", new BigDecimal("6"), result);
    }

    @Test
    public void testDivide() {
        BigDecimal result = calculatorService.divide(new BigDecimal("6"), new BigDecimal("3"));
        assertEquals("6 / 3 should equal 2", new BigDecimal("2"), result);
    }

    @Test
    public void testDivideByZero() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () ->
                calculatorService.divide(new BigDecimal("1"), BigDecimal.ZERO)
        );
        assertEquals("Error: Cannot divide by zero", exception.getMessage());
    }
}
