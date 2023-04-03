package io.ylab.intensive.lesson05.messagefilter.impl;

import io.ylab.intensive.lesson05.DbUtil;
import io.ylab.intensive.lesson05.messagefilter.abstracts.DbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

@Component
public class DbClientImpl implements DbClient {
    private final DataSource dataSource;

    @Autowired
    public DbClientImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            loadWordsFromFile(new File("words.txt"));
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadWordsFromFile(File file) throws SQLException, FileNotFoundException {
        createTable();
        cleanTable();

        String query = "insert into obscene_words (word) values (?)";
        try (Scanner scanner = new Scanner(file);
             Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            while (scanner.hasNextLine()){
                statement.setString(1, scanner.nextLine());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void cleanTable() throws SQLException {
        String query = "truncate table obscene_words";
        DbUtil.applyDdl(query, dataSource);
    }

    private void createTable() throws SQLException {
        String query = ""
                + "create table if not exists obscene_words (\n"
                + "word_id serial primary key,\n"
                + "word varchar\n"
                + ")";
        DbUtil.applyDdl(query, dataSource);
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
