package io.ylab.intensive.lesson03.transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        System.out.println("transliterate(\"HELLO! ПРИВЕТ! Go, boy!\") = " +
                transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!"));
        System.out.println("transliterate(\"МАРИЯ ИВАНОВА!\") = " +
                transliterator.transliterate("МАРИЯ ИВАНОВА!"));
    }
}