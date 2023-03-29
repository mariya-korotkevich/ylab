package io.ylab.intensive.lesson04.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Event;
import io.ylab.intensive.lesson04.eventsourcing.Message;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbApp {

    private static DataSource dataSource;
    public static void main(String[] args) throws Exception {
        dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();

        String exchangeName = "exc";
        String queueName = "queue";
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queueName, true, false, false, null);
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse messageResponse = channel.basicGet(queueName, true);
                if (messageResponse != null) {
                    messageProcessing(messageResponse);
                }
            }
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }

    private static void messageProcessing(GetResponse messageResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(messageResponse.getBody(), Message.class);
        if (Event.SAVE.equals(message.getEvent())){
            save(message.getPerson());
        } else if (Event.DELETE.equals(message.getEvent())){
            delete(message.getPerson());
        } else {
            System.err.println("Неизвестное событие: " + message);
        }
    }

    private static void save(Person person){
        String query = "insert into person (person_id, first_name, last_name, middle_name) values (?, ?, ?, ?)" +
                "ON CONFLICT (person_id) DO UPDATE SET first_name=?, last_name=?, middle_name=?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, person.getId());
            statement.setString(2, person.getName());
            statement.setString(3, person.getLastName());
            statement.setString(4, person.getMiddleName());
            statement.setString(5, person.getName());
            statement.setString(6, person.getLastName());
            statement.setString(7, person.getMiddleName());
            statement.execute();
            System.out.println("Добавлена/обновлена запись с id = " + person.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void delete(Person person){
        String query = "delete from person where person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, person.getId());
            System.out.println("Удаление записи с id = " + person.getId()
                    + ". Количество удаленных записей: " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}