package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    private final DataSource dataSource;

    @Autowired
    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        if (!containsTable(tableName)) {
            return null;
        }
        List<String> columns = getColumnsForTable(tableName);
        if (columns.size() == 0) {
            return null;
        }
        return buildSelectQuery(tableName, columns);
    }

    private List<String> getColumnsForTable(String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            try (ResultSet rs = databaseMetaData.getColumns(null, null, tableName, "%")) {
                while (rs.next()) {
                    columns.add(rs.getString("COLUMN_NAME"));
                }
            }
        }
        return columns;
    }

    private boolean containsTable(String tableName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            try (ResultSet rs = databaseMetaData.getTables(null, null, tableName, new String[]{"TABLE", "SYSTEM TABLE"})) {
                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    private String buildSelectQuery(String tableName, List<String> columns){
        StringBuilder builder = new StringBuilder();
        columns.forEach(s -> {
            builder.append(builder.length() == 0 ? "Select " : ", ");
            builder.append(s);
        });
        builder.append(" from ");
        builder.append(tableName);
        return builder.toString();
    }

    @Override
    public List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            try (ResultSet rs = databaseMetaData.getTables(null, null, "%", new String[]{"TABLE", "SYSTEM TABLE"})) {
                while (rs.next()) {
                    tables.add(rs.getString("table_name"));
                }
            }
        }
        return tables;
    }
}