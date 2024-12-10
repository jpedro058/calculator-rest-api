package com.calculatorrestapi.rest;

import com.calculatorrestapi.calculator.CalculatorApiApplication;
import com.calculatorrestapi.rest.controller.CalculatorController;
import com.calculatorrestapi.calculator.CalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CalculatorApiApplication.class)
@AutoConfigureMockMvc
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CalculatorService calculatorService;

    @InjectMocks
    private CalculatorController calculatorController;

    @Test
    void testRequestIdHeader() throws Exception {
        mockMvc.perform(get("/api/sum")
                        .param("a", "1")
                        .param("b", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("3"))
                .andExpect(result -> {
                    String requestId = result.getResponse().getHeader("X-Request-ID");
                    assertNotNull(requestId, "Request ID should not be null");
                    assertFalse(requestId.isEmpty(), "Request ID should not be empty");
                });
    }

    @Test
    void testSum() throws Exception {
        when(calculatorService.add(new BigDecimal("1"), new BigDecimal("2"))).thenReturn(new BigDecimal("3"));

        mockMvc.perform(get("/api/sum")
                        .param("a", "1")
                        .param("b", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("3"));
    }

    @Test
    void testSubtract() throws Exception {
        when(calculatorService.subtract(new BigDecimal("5"), new BigDecimal("3"))).thenReturn(new BigDecimal("2"));

        mockMvc.perform(get("/api/subtract")
                        .param("a", "5")
                        .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("2"));
    }

    @Test
    void testDivide() throws Exception {
        when(calculatorService.divide(new BigDecimal("6"), new BigDecimal("3"))).thenReturn(new BigDecimal("2"));

        mockMvc.perform(get("/api/divide")
                        .param("a", "6")
                        .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("2.0"));
    }

    @Test
    void testDivideByZero() throws Exception {
        when(calculatorService.divide(new BigDecimal("1"), new BigDecimal("0")))
                .thenThrow(new ArithmeticException("Division by zero"));

        mockMvc.perform(get("/api/divide")
                        .param("a", "1")
                        .param("b", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Cannot divide by zero"));
    }

    @Test
    void testMultiply() throws Exception {
        when(calculatorService.multiply(new BigDecimal("2"), new BigDecimal("3"))).thenReturn(new BigDecimal("6"));

        mockMvc.perform(get("/api/multiply")
                        .param("a", "2")
                        .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("6"));
    }
}

