package io.ylab.intensive.lesson05.eventsourcing.api.impl;

import io.ylab.intensive.lesson05.eventsourcing.Event;
import io.ylab.intensive.lesson05.eventsourcing.Message;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.api.abstracts.DbReader;
import io.ylab.intensive.lesson05.eventsourcing.api.abstracts.PersonApi;
import io.ylab.intensive.lesson05.eventsourcing.api.abstracts.RabbitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonApiImpl implements PersonApi {
    private final DbReader dbReader;
    private final RabbitClient rabbitClient;

    @Autowired
    public PersonApiImpl(DbReader dbReader, RabbitClient rabbitClient) {
        this.dbReader = dbReader;
        this.rabbitClient = rabbitClient;
    }

    @Override
    public void deletePerson(Long personId) {
        Person person = new Person();
        person.setId(personId);
        rabbitClient.sendMessage(new Message(person, Event.DELETE));
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        Person person = new Person(personId, firstName, lastName, middleName);
        rabbitClient.sendMessage(new Message(person, Event.SAVE));
    }

    @Override
    public Person findPerson(Long personId) {
        return dbReader.findPerson(personId);
    }

    @Override
    public List<Person> findAll() {
        return dbReader.findAll();
    }
}