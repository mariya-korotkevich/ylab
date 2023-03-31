package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;

import java.util.List;

public interface DbReader {
    Person findPerson(Long personId);

    List<Person> findAll();
}
