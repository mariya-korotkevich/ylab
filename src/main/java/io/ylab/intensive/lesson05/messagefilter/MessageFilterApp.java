package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.abstracts.MessageProcessor;
import io.ylab.intensive.lesson05.messagefilter.abstracts.RabbitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MessageFilterApp {
    private final RabbitClient rabbitClient;
    private final MessageProcessor messageProcessor;

    @Autowired
    public MessageFilterApp(RabbitClient rabbitClient, MessageProcessor messageProcessor) {
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
        while (!Thread.currentThread().isInterrupted()) {
            GetResponse messageResponse = rabbitClient.getMessage();
            if (messageResponse != null) {
                String filterMessage = messageProcessor.processing(messageResponse);
                rabbitClient.sendMessage(filterMessage);
            }
        }
    }
}