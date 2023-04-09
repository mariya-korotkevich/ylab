package io.ylab.intensive.lesson05.messagefilter.impl;

import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.abstracts.MessageProcessor;
import io.ylab.intensive.lesson05.messagefilter.abstracts.WordFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageProcessorImplTest {

    static WordFilter wordFilter;
    static MessageProcessor messageProcessor;

    @Mock
    GetResponse getResponse = mock(GetResponse.class);

    @BeforeAll
    static void init() {
        wordFilter = mock(WordFilter.class);
        when(wordFilter.filter("badWord")).thenReturn("b*****d");
        when(wordFilter.filter("goodWord")).thenReturn("goodWord");
        messageProcessor = new MessageProcessorImpl(wordFilter);
    }

    @Test
    void processing_ShouldReturnEmptyStringForEmptyString() {
        when(getResponse.getBody()).thenReturn("".getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals("", filterString);
    }

    @ParameterizedTest
    @CsvSource({"badWord,b*****d",
            "badWord goodWord,b*****d goodWord",
            "badWord.goodWord,b*****d.goodWord",
            "badWord;goodWord,b*****d;goodWord",
            "badWord!goodWord,b*****d!goodWord",
            "badWord?goodWord,b*****d?goodWord"})
    void processing_ShouldReturnFilterStringForStringWithBadWords(String input, String expected) {
        when(getResponse.getBody()).thenReturn(input.getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals(expected, filterString);
    }

    @ParameterizedTest
    @CsvSource({"badWord badWord,b*****d b*****d",
            "badWord.badWord,b*****d.b*****d",
            "badWord;badWord,b*****d;b*****d",
            "badWord!badWord,b*****d!b*****d",
            "badWord?badWord,b*****d?b*****d"})
    void processing_ShouldReturnFilterStringForStringWithOnlyBadWords(String input, String expected) {
        when(getResponse.getBody()).thenReturn(input.getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals(expected, filterString);
    }

    @ParameterizedTest
    @CsvSource({"goodWord,goodWord",
            "goodWord goodWord,goodWord goodWord",
            "goodWord.goodWord,goodWord.goodWord",
            "goodWord;goodWord,goodWord;goodWord",
            "goodWord!goodWord,goodWord!goodWord",
            "goodWord?goodWord,goodWord?goodWord"})
    void processing_ShouldReturnOriginalStringForStringWithOnlyGoodWords(String input, String expected) {
        when(getResponse.getBody()).thenReturn(input.getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals(expected, filterString);
    }
}