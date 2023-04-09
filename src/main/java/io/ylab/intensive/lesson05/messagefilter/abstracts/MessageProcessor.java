package io.ylab.intensive.lesson05.messagefilter.abstracts;

import com.rabbitmq.client.GetResponse;

public interface MessageProcessor {
    String processing(GetResponse response);
}
