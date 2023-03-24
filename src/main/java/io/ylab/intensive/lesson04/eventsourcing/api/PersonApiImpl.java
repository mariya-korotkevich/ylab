package io.ylab.intensive.lesson04.eventsourcing.api;

import io.ylab.intensive.lesson04.eventsourcing.Person;

import java.util.List;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {
  @Override
  public void deletePerson(Long personId) {
    
  }

  @Override
  public void savePerson(Long personId, String firstName, String lastName, String middleName) {

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
