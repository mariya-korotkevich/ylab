package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.abstracts.DbClient;
import io.ylab.intensive.lesson05.messagefilter.abstracts.MessageProcessor;
import io.ylab.intensive.lesson05.messagefilter.abstracts.RabbitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MessageFilterApp {
    private final DbClient dbClient;
    private final RabbitClient rabbitClient;
    private final MessageProcessor messageProcessor;

    @Autowired
    public MessageFilterApp(DbClient dbClient, RabbitClient rabbitClient, MessageProcessor messageProcessor) {
        this.dbClient = dbClient;
        this.rabbitClient = rabbitClient;
        this.messageProcessor = messageProcessor;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();

        MessageFilterApp app = applicationContext.getBean(MessageFilterApp.class);
        app.filterMessages();
    }

    public void filterMessages() {
        dbClient.loadWordsFromFile(new File("words.txt"));
        while (!Thread.currentThread().isInterrupted()) {
            GetResponse messageResponse = rabbitClient.getMessage();
            if (messageResponse != null) {
                String rightWords = messageProcessor.processing(messageResponse);
                rabbitClient.sendMessage(rightWords);
            }
        }
    }
}