package io.ylab.intensive.lesson05.eventsourcing.api;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi
        personApi.savePerson(5L, "Иван", "Иванов", "Иванович");
        Thread.sleep(1000);
        System.out.println("findPerson(5L) = " + personApi.findPerson(5L));

        personApi.deletePerson(5L);

        personApi.savePerson(2L, "Петр", "Петров", "Петрович");
        personApi.savePerson(3L, "Владимир", "Владимиров", "Владимирович");
        personApi.savePerson(4L, "Анна", "Иванова", "Владимировна");
        Thread.sleep(1000);

        System.out.println("findAll() = " + personApi.findAll());
    }
}