package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.eventsourcing.Event;
import io.ylab.intensive.lesson05.eventsourcing.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageProcessorImpl implements MessageProcessor{
    private final DbClient dbClient;

    @Autowired
    public MessageProcessorImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public void messageProcessing(GetResponse messageResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(messageResponse.getBody(), Message.class);
        if (Event.SAVE.equals(message.getEvent())) {
            dbClient.save(message.getPerson());
        } else if (Event.DELETE.equals(message.getEvent())) {
            dbClient.delete(message.getPerson());
        } else {
            System.err.println("Неизвестное событие: " + message);
        }
    }
}