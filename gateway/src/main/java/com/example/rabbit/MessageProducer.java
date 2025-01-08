package com.example.rabbit;

import com.example.configuration.RabbitConfig;
import com.example.rabbit.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final RabbitConfig rabbitConfig;


    public void sendMessage(Message message) {
        rabbitTemplate.convertAndSend(rabbitConfig.queueName, convertToString(message));
        log.info("Message request sent in rabbit. (Message: {})", message);
    }

    private String convertToString(Message message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}