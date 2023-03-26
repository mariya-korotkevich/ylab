package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {
    private final ConnectionFactory connectionFactory;

    public PersonApiImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    @Override
    public void deletePerson(Long personId) {

    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        String exchangeName = "exc";
        String queueName = "queue";
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, "*");
            channel.basicPublish(exchangeName, "key", null, "Hello World".getBytes());
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person findPerson(Long personId) {
        return null;
    }

    @Override
    public List<Person> findAll() {
        return null;
    }
}
