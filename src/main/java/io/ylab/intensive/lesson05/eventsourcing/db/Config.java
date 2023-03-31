package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.DbUtil;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Config {

  @Bean
  public DataSource dataSource() throws SQLException {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setServerName("localhost");
    dataSource.setUser("postgres");
    dataSource.setPassword("postgres");
    dataSource.setDatabaseName("postgres");
    dataSource.setPortNumber(5432);

    String ddl = ""
                     + "drop table if exists person;"
                     + "create if not exists table person (\n"
                     + "person_id bigint primary key,\n"
                     + "first_name varchar,\n"
                     + "last_name varchar,\n"
                     + "middle_name varchar\n"
                     + ")";
    DbUtil.applyDdl(ddl, dataSource);
    
    return dataSource;
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("localhost");
    connectionFactory.setPort(5672);
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    connectionFactory.setVirtualHost("/");
    return connectionFactory;
  }
  
}
