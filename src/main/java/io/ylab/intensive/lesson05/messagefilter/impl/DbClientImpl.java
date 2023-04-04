package io.ylab.intensive.lesson05.messagefilter.impl;

import io.ylab.intensive.lesson05.DbUtil;
import io.ylab.intensive.lesson05.messagefilter.abstracts.DbClient;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DbClientImpl implements DbClient {
    private final DataSource dataSource;

    public DbClientImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadWordsFromFile(File file) throws SQLException, FileNotFoundException {

        prepareTable();

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

    private void prepareTable() throws SQLException {
        String query = ""
                + "create table if not exists obscene_words (\n"
                + "word_id serial primary key,\n"
                + "word varchar);\n"
                + "truncate table obscene_words;";
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
