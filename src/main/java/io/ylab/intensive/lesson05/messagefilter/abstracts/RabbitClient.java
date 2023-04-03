package io.ylab.intensive.lesson05.messagefilter.abstracts;

import com.rabbitmq.client.GetResponse;

public interface RabbitClient {
    GetResponse getMessage();

    void sendMessage(String filterMessage);
}
