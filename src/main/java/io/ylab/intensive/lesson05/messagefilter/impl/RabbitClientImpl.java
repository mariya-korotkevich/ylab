package io.ylab.intensive.lesson05.messagefilter.impl;

import com.rabbitmq.client.*;
import io.ylab.intensive.lesson05.messagefilter.abstracts.RabbitClient;
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
    public GetResponse getMessage() {
        String queueName = "input";
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, true, false, false, null);
            return channel.basicGet(queueName, true);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(String filterMessage) {
        String queueName = "output";
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicPublish("", queueName, null, filterMessage.getBytes());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
