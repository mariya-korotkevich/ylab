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
        Person person = new Person();
        person.setId(personId);
        person.setName(firstName);
        person.setLastName(lastName);
        person.setMiddleName(middleName);

        sendMessage(new Message(person, Event.SAVE));
    }

    private void sendMessage(Message message){

        byte[] messageBytes;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            messageBytes = objectMapper.writeValueAsBytes(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String exchangeName = "exc";
        String queueName = "queue";
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, "*");

            channel.basicPublish(exchangeName, "*", null, messageBytes);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person findPerson(Long personId) {
        String query = "select person_id, first_name, last_name, middle_name from person where person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, personId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                Person person = new Person();
                person.setId(rs.getLong(1));
                person.setName(rs.getString(2));
                person.setLastName(rs.getString(3));
                person.setMiddleName(rs.getString(4));
                return person;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Person> findAll() {
        String query = "select person_id, first_name, last_name, middle_name from person";
        try (java.sql.Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            List<Person> people = new ArrayList<>();
            while (rs.next()){
                Person person = new Person();
                person.setId(rs.getLong(1));
                person.setName(rs.getString(2));
                person.setLastName(rs.getString(3));
                person.setMiddleName(rs.getString(4));
                people.add(person);
            }
            return people;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}