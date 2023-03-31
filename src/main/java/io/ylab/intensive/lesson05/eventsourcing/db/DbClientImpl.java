package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class DbClientImpl implements DbClient{

    private final DataSource dataSource;

    @Autowired
    public DbClientImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Person person) {
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

    @Override
    public void delete(Person person) {
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