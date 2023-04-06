package io.ylab.intensive.lesson05.messagefilter.impl;

import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.abstracts.MessageProcessor;
import io.ylab.intensive.lesson05.messagefilter.abstracts.WordFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
    static void init(){
        wordFilter = mock(WordFilter.class);
        when(wordFilter.filter("badWord")).thenReturn("b*****d");
        when(wordFilter.filter("goodWord")).thenReturn("goodWord");
        messageProcessor = new MessageProcessorImpl(wordFilter);
    }

    @Test
    void processingWhenEmptyStringThenReturnEmptyString() {
        when(getResponse.getBody()).thenReturn("".getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals("", filterString);
    }

    @Test
    void processingWhenStringWithOneBadWordThenReturnFilterString() {
        when(getResponse.getBody()).thenReturn("badWord".getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals("b*****d", filterString);
    }

    @Test
    void processingWhenStringWithOneGoodWordThenReturnOriginalString() {
        when(getResponse.getBody()).thenReturn("goodWord".getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals("goodWord", filterString);
    }

    @Test
    void processingWhenBadWordAndGoodWordAndSpaceThenReturnFilterString() {
        when(getResponse.getBody()).thenReturn("badWord goodWord".getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals("b*****d goodWord", filterString);
    }

    @Test
    void processingWhenBadWordAndGoodWordAndPointThenReturnFilterString() {
        when(getResponse.getBody()).thenReturn("badWord.goodWord".getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals("b*****d.goodWord", filterString);
    }

    @Test
    void processingWhenBadWordAndGoodWordAndSemicolonThenReturnFilterString() {
        when(getResponse.getBody()).thenReturn("badWord;goodWord".getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals("b*****d;goodWord", filterString);
    }

    @Test
    void processingWhenBadWordAndGoodWordAndExclamationPointThenReturnFilterString() {
        when(getResponse.getBody()).thenReturn("badWord!goodWord".getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals("b*****d!goodWord", filterString);
    }

    @Test
    void processingWhenBadWordAndGoodWordAndQuestionMarkThenReturnFilterString() {
        when(getResponse.getBody()).thenReturn("badWord?goodWord".getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals("b*****d?goodWord", filterString);
    }

    @Test
    void processingWhenBadWordAndGoodWordAndLineSeparatorThenReturnFilterString() {
        when(getResponse.getBody()).thenReturn(("badWord" + System.lineSeparator() + "goodWord").getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals("b*****d" + System.lineSeparator() + "goodWord", filterString);
    }

    @Test
    void processingWhenOnlyGoodWordsThenReturnOriginalString() {
        when(getResponse.getBody()).thenReturn("goodWord.goodWord goodWord;goodWord,goodWord?goodWord!goodWord".getBytes());
        String filterString = messageProcessor.processing(getResponse);
        assertEquals("goodWord.goodWord goodWord;goodWord,goodWord?goodWord!goodWord", filterString);
    }
}