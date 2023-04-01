package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DbApp {
    private final MessageProcessorImpl messageProcessor;
    private final RabbitClient rabbitClient;

    @Autowired
    public DbApp(MessageProcessorImpl messageProcessor, RabbitClient rabbitClient) {
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
            GetResponse messageResponse = rabbitClient.readMessage();
            if (messageResponse != null) {
                messageProcessor.messageProcessing(messageResponse);
            }
        }
    }
}