package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;

import javax.sql.DataSource;

public class ApiApp {
    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = initMQ();
        DataSource dataSource = DbUtil.buildDataSource();

        PersonApi personApi = new PersonApiImpl(connectionFactory, dataSource);
        personApi.savePerson(1L, "Иван", "Иванов", "Иванович");
        Thread.sleep(1000);
        System.out.println("findPerson(1L) = " + personApi.findPerson(1L));

        personApi.deletePerson(1L);

        personApi.savePerson(2L, "Петр", "Петров", "Петрович");
        personApi.savePerson(3L, "Владимир", "Владимиров", "Владимирович");
        personApi.savePerson(4L, "Анна", "Иванова", "Владимировна");
        Thread.sleep(1000);

        System.out.println("findAll() = " + personApi.findAll());
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
