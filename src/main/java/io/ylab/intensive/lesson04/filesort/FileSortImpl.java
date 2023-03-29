package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

public class FileSortImpl implements FileSorter {
    private DataSource dataSource;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        writeData(data);
        File result = getSortedFile();
        deleteData();
        return result;
    }

    private void writeData(File data) {
        String query = "insert into numbers (val) values (?)";
        try (Scanner scanner = new Scanner(data);
             Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            while (scanner.hasNextLong()) {
                statement.setLong(1, scanner.nextLong());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private File getSortedFile() {
        File sortedFile = new File("sorted_data.txt");
        String query = "select val from numbers order by val desc";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             PrintWriter pw = new PrintWriter(sortedFile);
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                pw.println(rs.getLong(1));
            }
            pw.flush();
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return sortedFile;
    }

    private void deleteData() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("truncate table numbers");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}