package io.ylab.intensive.lesson05.messagefilter.impl;

import io.ylab.intensive.lesson05.messagefilter.abstracts.DbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    @Override
    public boolean containsWord(String word) {
        String query = "Select exists(\n" +
                "select * from obscene_words\n" +
                "where word ilike ?)";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, word);
            try (ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
