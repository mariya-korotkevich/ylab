package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.GetResponse;

public interface RabbitClient {
    GetResponse readMessage() throws Exception;
}
