package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DbApp {
    private final MessageProcessorImpl messageProcessor;
    private final ConnectionFactory connectionFactory;

    @Autowired
    public DbApp(MessageProcessorImpl messageProcessor, ConnectionFactory connectionFactory) {
        this.messageProcessor = messageProcessor;
        this.connectionFactory = connectionFactory;
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        // тут пишем создание и запуск приложения работы с БД
        DbApp dbApp = applicationContext.getBean(DbApp.class);
        dbApp.readMessages();
    }

    public void readMessages() throws Exception {
        String exchangeName = "exc";
        String queueName = "queue";
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queueName, true, false, false, null);
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse messageResponse = channel.basicGet(queueName, true);
                if (messageResponse != null) {
                    messageProcessor.messageProcessing(messageResponse);
                }
            }
        }
    }
}