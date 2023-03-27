package io.ylab.intensive.lesson04.persistentmap;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private DataSource dataSource;
    private String mapName;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.mapName = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        String query = "select exists(select * from persistent_map where map_name = ? and key = ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mapName);
            statement.setString(2, key);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                return rs.getBoolean(1);
            }
        }
        return false;
    }

    @Override
    public List<String> getKeys() throws SQLException {
        String query = "select key from persistent_map where map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mapName);
            ResultSet rs = statement.executeQuery();
            List<String> result = new ArrayList<>();
            while (rs.next()){
                result.add(rs.getString(1));
            }
            return result;
        }
    }

    @Override
    public String get(String key) throws SQLException {
        String query = "select value from persistent_map where map_name = ? and key = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mapName);
            statement.setString(2, key);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                return rs.getString(1);
            }
        }
        return null;
    }

    @Override
    public void remove(String key) throws SQLException {
        String query = "delete from persistent_map where map_name = ? and key = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mapName);
            statement.setString(2, key);
            statement.execute();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        String query = "delete from persistent_map where map_name = ? and key = ?;\n" +
                       "insert into persistent_map (map_name, KEY, value) values (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mapName);
            statement.setString(2, key);
            statement.setString(3, mapName);
            statement.setString(4, key);
            statement.setString(5, value);
            statement.execute();
        }
    }

    @Override
    public void clear() throws SQLException {
        String query = "delete from persistent_map where map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mapName);
            statement.execute();
        }
    }
}