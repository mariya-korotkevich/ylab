package io.ylab.intensive.lesson05.eventsourcing.api.impl;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.api.abstracts.DbReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbReaderImp implements DbReader {
    private final DataSource dataSource;

    @Autowired
    public DbReaderImp(DataSource dataSource) {
        this.dataSource = dataSource;
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