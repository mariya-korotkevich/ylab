package io.ylab.intensive.lesson05.messagefilter.impl;

import io.ylab.intensive.lesson05.messagefilter.abstracts.WordFilter;
import org.springframework.stereotype.Component;

@Component
public class WordFilterImpl implements WordFilter {
    @Override
    public String filter(String s) {
        return s;
    }
}
