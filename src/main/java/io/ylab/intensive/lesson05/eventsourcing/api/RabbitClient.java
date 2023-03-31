package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Message;

public interface RabbitClient {
    void sendMessage(Message message);
}
