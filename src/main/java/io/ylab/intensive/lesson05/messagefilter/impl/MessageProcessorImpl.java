package io.ylab.intensive.lesson05.messagefilter.impl;

import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.abstracts.WordFilter;
import io.ylab.intensive.lesson05.messagefilter.abstracts.MessageProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageProcessorImpl implements MessageProcessor {
    private final WordFilter wordFilter;
    private final List<Character> wordSeparators = List.of('.', ',', ' ', ';', '?', '!', '\r', '\n');

    public MessageProcessorImpl(WordFilter wordFilter) {
        this.wordFilter = wordFilter;
    }

    @Override
    public String processing(GetResponse response) {

        String originalString = new String(response.getBody());

        StringBuilder word = new StringBuilder();
        StringBuilder filterStringBuilder = new StringBuilder();
        for (int i = 0; i < originalString.length(); i++) {
            char currentChar = originalString.charAt(i);
            if (!isWordSeparator(currentChar)) {
                word.append(currentChar);
            } else {
                filterStringBuilder.append(getFilterWord(word));
                filterStringBuilder.append(currentChar);
            }
        }
        filterStringBuilder.append(getFilterWord(word));
        return filterStringBuilder.toString();
    }

    private String getFilterWord(StringBuilder word){
        String result = "";
        if (word.length() != 0){
            result = wordFilter.filter(word.toString());
            word.setLength(0);
        }
        return result;
    }

    private boolean isWordSeparator(char c) {
        return wordSeparators.contains(c);
    }
}