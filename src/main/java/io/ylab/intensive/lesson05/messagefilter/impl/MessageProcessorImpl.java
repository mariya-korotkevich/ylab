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

        String originalString = new String(response.getBody());

        StringBuilder word = new StringBuilder();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < originalString.length(); i++) {
            char currentChar = originalString.charAt(i);
            if (!isWordSeparator(currentChar)) {
                word.append(currentChar);
            } else {
                if (word.length() != 0){
                    result.append(wordFilter.filter(word.toString()));
                    word.setLength(0);
                }
                result.append(currentChar);
            }
        }
        return result.toString();
    }

    private boolean isWordSeparator(char c) {
        return c == '.'
                || c == ','
                || c == ' '
                || c == ';'
                || c == '?'
                || c == '!';
    }
}