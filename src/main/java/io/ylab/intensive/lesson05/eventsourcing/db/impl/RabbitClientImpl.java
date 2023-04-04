package io.ylab.intensive.lesson05.eventsourcing.db.impl;

import com.rabbitmq.client.*;
import io.ylab.intensive.lesson05.eventsourcing.db.abstracts.RabbitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitClientImpl implements RabbitClient {
    private final ConnectionFactory connectionFactory;

    @Autowired
    public RabbitClientImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public GetResponse getMessage() throws Exception {
        String exchangeName = "exc";
        String queueName = "queue";
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queueName, true, false, false, null);
            return channel.basicGet(queueName, true);
        }
    }
}