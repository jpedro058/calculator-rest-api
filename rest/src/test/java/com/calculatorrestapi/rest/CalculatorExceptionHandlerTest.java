package com.calculatorrestapi.rest;

import com.calculatorrestapi.rest.controller.CalculatorController;
import com.calculatorrestapi.calculator.CalculatorApiApplication;
import com.calculatorrestapi.calculator.CalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CalculatorApiApplication.class)
@AutoConfigureMockMvc
class CalculatorExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CalculatorService calculatorService;

    @InjectMocks
    private CalculatorController calculatorController;


    @Test
    void testDivideByZero() throws Exception {
        when(calculatorService.divide(new BigDecimal("1"), new BigDecimal("0")))
                .thenThrow(new ArithmeticException("Error: Cannot divide by zero"));

        mockMvc.perform(get("/api/divide").param("a", "1").param("b", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Cannot divide by zero"));

    }

}


