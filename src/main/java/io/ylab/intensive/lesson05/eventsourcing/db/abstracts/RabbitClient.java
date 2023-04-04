package io.ylab.intensive.lesson05.eventsourcing.db.abstracts;

import com.rabbitmq.client.GetResponse;

public interface RabbitClient {
    GetResponse getMessage() throws Exception;
}
