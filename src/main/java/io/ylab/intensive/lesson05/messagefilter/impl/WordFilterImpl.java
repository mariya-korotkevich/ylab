package io.ylab.intensive.lesson05.messagefilter.impl;

import io.ylab.intensive.lesson05.messagefilter.abstracts.DbClient;
import io.ylab.intensive.lesson05.messagefilter.abstracts.WordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WordFilterImpl implements WordFilter {

    private final DbClient dbClient;

    @Autowired
    public WordFilterImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public String filter(String word) {
        if (!dbClient.containsWord(word)){
            return word;
        }
        char[] chars = word.toCharArray();
        for (int i = 1; i < chars.length - 1; i++) {
            chars[i] = '*';
        }
        return new String(chars);
    }
}
