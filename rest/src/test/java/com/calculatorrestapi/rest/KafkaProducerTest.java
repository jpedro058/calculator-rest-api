package com.calculatorrestapi.rest;

import com.calculatorrestapi.calculator.CalculatorApiApplication;
import com.calculatorrestapi.rest.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = CalculatorApiApplication.class)
@AutoConfigureMockMvc
public class KafkaProducerTest {

    @MockBean  // Use @MockBean to mock the KafkaProducer
    private KafkaProducer kafkaProducer;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSendMessage() throws Exception {
        String operation = "add";
        String a = "5";
        String b = "3";
        String requestId = "test-request-id";

        // Mock the requestId in MDC
        MDC.put("requestId", requestId);

        // Perform the request
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/send")
                        .param("operation", operation)
                        .param("a", a)
                        .param("b", b))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Extract the dynamic requestId from the response (regex)
        String expectedMessage = String.format("requestId: %s, operation: %s, a: %s, b: %s",
                result.split("requestId: ")[1].split(",")[0], operation, a, b);

        // Verify if the actual message contains the expected one
        assert result.contains(String.format("Message sent to Kafka topic: %s", expectedMessage));

        // Verify that the Kafka producer sent the correct message
        verify(kafkaProducer, times(1)).sendMessage("calculator-topic", expectedMessage);

        // Clear MDC after the test
        MDC.clear();
    }
}
