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
        String query = "select exists(\n"
                + "\tselect * from persistent_map\n"
                + "\twhere map_name = ? and key = ?) as exists\n"
                + ";";
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
        String query = "select key\n"
                + "\tfrom persistent_map\n"
                + "\twhere map_name = ?\n"
                + ";";
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
        String query = "select value\n"
                + "\tfrom persistent_map\n"
                + "\twhere map_name = ? and key = ?\n"
                + ";";
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
        String query = "delete from persistent_map\n"
                + "\twhere map_name = ? and key = ?\n"
                + ";";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mapName);
            statement.setString(2, key);
            statement.execute();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        if (containsKey(key)) {
            updateValue(key, value);
        } else {
            setValue(key, value);
        }
    }

    private void updateValue(String key, String value) throws SQLException {
        String query = "update persistent_map\n"
                    + "\tset value = ?\n"
                    + "\twhere map_name = ? and key = ?\n"
                    + ";";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, value);
            statement.setString(2, mapName);
            statement.setString(3, key);
            statement.execute();
        }
    }

    private void setValue(String key, String value) throws SQLException{
        String query = "insert into persistent_map\n"
                    + "\t(map_name, KEY, value)\n"
                    + "\tvalues (?, ?, ?)\n"
                    + ";";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mapName);
            statement.setString(2, key);
            statement.setString(3, value);
            statement.execute();
        }
    }

    @Override
    public void clear() throws SQLException {
        String query = "delete from persistent_map\n"
                + "\twhere map_name = ?\n"
                + ";";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mapName);
            statement.execute();
        }
    }
}