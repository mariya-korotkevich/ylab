package io.ylab.intensive.lesson05.eventsourcing.db.abstracts;

import io.ylab.intensive.lesson05.eventsourcing.Person;

public interface DbClient {
    void save(Person person);

    void delete(Person person);
}