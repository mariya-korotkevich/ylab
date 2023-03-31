package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.GetResponse;

import java.io.IOException;

public interface MessageProcessor {
    void messageProcessing(GetResponse messageResponse) throws IOException;
}