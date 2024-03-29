package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.*;
import io.ylab.intensive.lesson05.eventsourcing.db.abstracts.MessageProcessor;
import io.ylab.intensive.lesson05.eventsourcing.db.abstracts.RabbitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DbApp {
    private final MessageProcessor messageProcessor;
    private final RabbitClient rabbitClient;

    @Autowired
    public DbApp(MessageProcessor messageProcessor, RabbitClient rabbitClient) {
        this.messageProcessor = messageProcessor;
        this.rabbitClient = rabbitClient;
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        // тут пишем создание и запуск приложения работы с БД
        DbApp dbApp = applicationContext.getBean(DbApp.class);
        dbApp.readMessages();
    }

    public void readMessages() throws Exception {
        while (!Thread.currentThread().isInterrupted()) {
            GetResponse messageResponse = rabbitClient.getMessage();
            if (messageResponse != null) {
                messageProcessor.messageProcessing(messageResponse);
            }
        }
    }
}