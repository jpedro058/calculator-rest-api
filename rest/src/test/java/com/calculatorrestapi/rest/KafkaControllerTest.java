package com.calculatorrestapi.rest;

import com.calculatorrestapi.rest.kafka.KafkaProducer;
import com.calculatorrestapi.calculator.CalculatorApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = CalculatorApiApplication.class)
@AutoConfigureMockMvc
public class KafkaControllerTest {

    @MockBean //Although MockBean is deprecated I used it because it is the only way that was working for me
    private KafkaProducer kafkaProducer;  // Mock KafkaProducer for the context

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSendMessage() throws Exception {
        String operation = "add";
        String a = "5";
        String b = "3";

        mockMvc.perform(MockMvcRequestBuilders.get("/send")
                        .param("operation", operation)
                        .param("a", a)
                        .param("b", b))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Message sent to Kafka topic: operation: add, a: 5, b: 3"));

        verify(kafkaProducer, times(1)).sendMessage("calculator-topic", "operation: add, a: 5, b: 3");
    }
}
