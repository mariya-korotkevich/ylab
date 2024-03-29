package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.messagefilter.abstracts.DbClient;
import io.ylab.intensive.lesson05.messagefilter.impl.DbClientImpl;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@Configuration
@ComponentScan("io.ylab.intensive.lesson05.messagefilter")
public class Config {

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

    @Bean
    public DataSource dataSource() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDatabaseName("postgres");
        dataSource.setPortNumber(5432);
        return dataSource;
    }

    @Bean
    @Autowired
    public DbClient dbClient(DataSource dataSource) throws SQLException, FileNotFoundException {
        DbClient dbClient = new DbClientImpl(dataSource);
        dbClient.loadWordsFromFile(new File("words.txt"));
        return dbClient;
    }
}