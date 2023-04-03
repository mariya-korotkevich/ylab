package io.ylab.intensive.lesson05.messagefilter.abstracts;

import java.io.File;

public interface DbClient {
    void loadWordsFromFile(File file);

    boolean containsWord(String word);
}
