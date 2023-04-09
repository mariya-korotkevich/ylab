package io.ylab.intensive.lesson05.eventsourcing.api.abstracts;

import io.ylab.intensive.lesson05.eventsourcing.Message;

public interface RabbitClient {
    void sendMessage(Message message);
}
