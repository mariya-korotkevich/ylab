package io.ylab.intensive.lesson04.eventsourcing.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import io.ylab.intensive.lesson04.eventsourcing.Event;
import io.ylab.intensive.lesson04.eventsourcing.Message;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {
    private final ConnectionFactory connectionFactory;
    private final DataSource dataSource;

    public PersonApiImpl(ConnectionFactory connectionFactory, DataSource dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {
        Person person = new Person();
        person.setId(personId);
        sendMessage(new Message(person, Event.DELETE));
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        Person person = new Person(personId, firstName, lastName, middleName);
        sendMessage(new Message(person, Event.SAVE));
    }

    private void sendMessage(Message message) {
        String exchangeName = "exc";
        String queueName = "queue";
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, "*");
            byte[] bytes = getBytes(message);
            if (bytes != null && bytes.length > 0) {
                channel.basicPublish(exchangeName, "*", null, bytes);
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

    @Override
    public Person findPerson(Long personId) {
        Person findPerson = null;
        String query = "select person_id,\n" +
                "first_name,\n" +
                "last_name,\n" +
                "middle_name\n" +
                "from person\n" +
                "where person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, personId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    findPerson = new Person(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findPerson;
    }

    @Override
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();
        String query = "select person_id,\n" +
                "first_name,\n" +
                "last_name,\n" +
                "middle_name\n" +
                "from person";
        try (java.sql.Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Person person = new Person(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }
}