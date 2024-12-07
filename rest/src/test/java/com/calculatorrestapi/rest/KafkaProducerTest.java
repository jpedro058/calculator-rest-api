package com.calculatorrestapi.rest;

import com.calculatorrestapi.rest.kafka.KafkaProducer;
import com.calculatorrestapi.calculator.CalculatorApiApplication;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = CalculatorApiApplication.class)  // Mantém o contexto completo da aplicação
public class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @Test
    public void testSendMessage() {
        String topic = "calculator-topic";
        String message = "operation: add, a: 5, b: 3";

        kafkaProducer.sendMessage(topic, message);

        // Verifica se o KafkaTemplate enviou a mensagem para o tópico correto
        verify(kafkaTemplate, times(1)).send(topic, message);
    }
}
