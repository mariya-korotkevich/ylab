package io.ylab.intensive.lesson05.messagefilter.impl;

import io.ylab.intensive.lesson05.messagefilter.abstracts.DbClient;
import io.ylab.intensive.lesson05.messagefilter.abstracts.WordFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WordFilterImplTest {

    static DbClient dbClient;
    static WordFilter wordFilter;

    @BeforeAll
    static void init(){
        dbClient = mock(DbClient.class);
        when(dbClient.containsWord("")).thenReturn(false);
        when(dbClient.containsWord("Obscene")).thenReturn(true);
        when(dbClient.containsWord("NotObscene")).thenReturn(false);
        when(dbClient.containsWord(null)).thenReturn(false);

        wordFilter = new WordFilterImpl(dbClient);
    }

    @Test
    void filterWhenEmptyWordThenReturnEmptyWord() {
        String filterWord = wordFilter.filter("");
        assertEquals("", filterWord);
    }

    @Test
    void filterWhenObsceneWordThenReturnFilterWord() {
        String filterWord = wordFilter.filter("Obscene");
        assertEquals("O*****e", filterWord);
    }

    @Test
    void filterWhenNotObsceneWordThenReturnOriginalWord() {
        String filterWord = wordFilter.filter("NotObscene");
        assertEquals("NotObscene", filterWord);
    }

    @Test
    void filterWhenNullThenReturnNull() {
        String filterWord = wordFilter.filter(null);
        assertNull(filterWord);
    }
}