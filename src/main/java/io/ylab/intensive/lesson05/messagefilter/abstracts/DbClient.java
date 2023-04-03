package io.ylab.intensive.lesson05.messagefilter.abstracts;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface DbClient {
    void loadWordsFromFile(File file) throws SQLException, FileNotFoundException;

    boolean containsWord(String word);
}
