package io.ylab.intensive.lesson05.messagefilter.impl;

import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.abstracts.WordFilter;
import io.ylab.intensive.lesson05.messagefilter.abstracts.MessageProcessor;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessorImpl implements MessageProcessor {
    private final WordFilter wordFilter;

    public MessageProcessorImpl(WordFilter wordFilter) {
        this.wordFilter = wordFilter;
    }

    @Override
    public String processing(GetResponse response) {
        String s = new String(response.getBody());
        System.out.println(s);
        return s;
    }
}
