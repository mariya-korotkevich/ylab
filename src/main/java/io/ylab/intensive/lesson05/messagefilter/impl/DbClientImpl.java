package io.ylab.intensive.lesson05.messagefilter.impl;

import io.ylab.intensive.lesson05.messagefilter.abstracts.DbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

@Component
public class DbClientImpl implements DbClient {
    private final DataSource dataSource;

    @Autowired
    public DbClientImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadWordsFromFile(File file) {
        String query = "insert into obscene_words (word) values (?)";
        try (Scanner scanner = new Scanner(file);
             Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            while (scanner.hasNextLine()){
                statement.setString(1, scanner.nextLine());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
