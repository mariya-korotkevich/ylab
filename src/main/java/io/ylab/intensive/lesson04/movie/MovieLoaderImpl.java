package io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        List<Movie> movies = readCsvFile(file);
        writeListToDB(movies);
    }

    private List<Movie> readCsvFile(File csvFile) {
        List<Movie> movies = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(csvFile))) {
            scanner.nextLine();
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                Movie movie = readMovie(scanner.nextLine());
                movies.add(movie);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }

    private Movie readMovie(String lineFromCsvFile) {
        String[] data = lineFromCsvFile.split(";");

        Movie movie = new Movie();
        movie.setYear(Integer.valueOf(data[0]));
        movie.setLength(data[1].isEmpty() ? null : Integer.valueOf(data[1]));
        movie.setTitle(data[2].isEmpty() ? null : data[2]);
        movie.setSubject(data[3].isEmpty() ? null : data[3]);
        movie.setActors(data[4].isEmpty() ? null : data[4]);
        movie.setActress(data[5].isEmpty() ? null : data[5]);
        movie.setDirector(data[6].isEmpty() ? null : data[6]);
        movie.setPopularity(data[7].isEmpty() ? null : Integer.valueOf(data[7]));
        movie.setAwards(data[8].isEmpty() ? null : Boolean.valueOf(data[8]));
        return movie;
    }
    private void writeListToDB(List<Movie> movies) {
        String query = "insert into movie\n"
                + "(year, length, title, subject, actors, actress, director, popularity, awards)\n"
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (Movie movie : movies) {
                setInt(statement, 1, movie.getYear());
                setInt(statement, 2, movie.getLength());
                setString(statement, 3, movie.getTitle());
                setString(statement, 4, movie.getSubject());
                setString(statement, 5, movie.getActors());
                setString(statement, 6, movie.getActress());
                setString(statement, 7, movie.getDirector());
                setInt(statement, 8, movie.getPopularity());
                setBoolean(statement, 9, movie.getAwards());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setInt(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }

    private void setString(PreparedStatement statement, int index, String value) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.VARCHAR);
        } else {
            statement.setString(index, value);
        }
    }

    private void setBoolean(PreparedStatement statement, int index, Boolean value) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.BOOLEAN);
        } else {
            statement.setBoolean(index, value);
        }
    }
}