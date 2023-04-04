package io.ylab.intensive.lesson05.eventsourcing.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.eventsourcing.Message;
import io.ylab.intensive.lesson05.eventsourcing.api.abstracts.RabbitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class RabbitClientImpl implements RabbitClient {
    private final ConnectionFactory connectionFactory;

    @Autowired
    public RabbitClientImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void sendMessage(Message message) {
        String queueName = "queue";
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, true, false, false, null);
            byte[] bytes = getBytes(message);
            if (bytes != null && bytes.length > 0) {
                channel.basicPublish("", queueName, null, bytes);
            } else {
                System.err.println("Не удалось отправить сообщение: " + message);
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytes(Message message) {
        byte[] bytes = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            bytes = objectMapper.writeValueAsBytes(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}