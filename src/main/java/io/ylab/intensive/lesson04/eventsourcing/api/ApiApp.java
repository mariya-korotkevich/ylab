package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;

public class ApiApp {
    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = initMQ();

        // Тут пишем создание PersonApi, запуск и демонстрацию работы

        PersonApi personApi = new PersonApiImpl(connectionFactory);
        personApi.savePerson(1L, "Иван", "Иванов", "Иванович");
        //System.out.println("findPerson(1L) = " + personApi.findPerson(1L));

//        personApi.deletePerson(1L);
//
//        personApi.savePerson(2L, "Петр", "Петров", "Петрович");
//        personApi.savePerson(3L, "Владимир", "Владимиров", "Владимирович");
//        personApi.savePerson(4L, "Анна", "Иванова", "Владимировна");
//
//        System.out.println("findAll() = " + personApi.findAll());
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
