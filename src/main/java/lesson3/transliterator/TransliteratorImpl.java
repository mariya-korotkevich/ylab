package lesson3.transliterator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TransliteratorImpl implements Transliterator {
    private static final Map<Character, String> DICTIONARY;

    static {
        DICTIONARY = new HashMap<>();
        DICTIONARY.put('А', "A");
        DICTIONARY.put('Б', "B");
        DICTIONARY.put('В', "V");
        DICTIONARY.put('Г', "G");
        DICTIONARY.put('Д', "D");
        DICTIONARY.put('Е', "E");
        DICTIONARY.put('Ё', "E");
        DICTIONARY.put('Ж', "ZH");
        DICTIONARY.put('З', "Z");
        DICTIONARY.put('И', "I");
        DICTIONARY.put('Й', "I");
        DICTIONARY.put('К', "K");
        DICTIONARY.put('Л', "L");
        DICTIONARY.put('М', "M");
        DICTIONARY.put('Н', "N");
        DICTIONARY.put('О', "O");
        DICTIONARY.put('П', "P");
        DICTIONARY.put('Р', "R");
        DICTIONARY.put('С', "S");
        DICTIONARY.put('Т', "T");
        DICTIONARY.put('У', "U");
        DICTIONARY.put('Ф', "F");
        DICTIONARY.put('Х', "KH");
        DICTIONARY.put('Ц', "TS");
        DICTIONARY.put('Ч', "CH");
        DICTIONARY.put('Ш', "SH");
        DICTIONARY.put('Щ', "SHCH");
        DICTIONARY.put('Ы', "Y");
        DICTIONARY.put('Ь', "");
        DICTIONARY.put('Ъ', "IE");
        DICTIONARY.put('Э', "E");
        DICTIONARY.put('Ю', "IU");
        DICTIONARY.put('Я', "IA");
    }

    @Override
    public String transliterate(String source) {
        return source.chars()
                .mapToObj(i -> (char) i)
                .map(c -> DICTIONARY.getOrDefault(c, String.valueOf(c)))
                .collect(Collectors.joining());
    }
}