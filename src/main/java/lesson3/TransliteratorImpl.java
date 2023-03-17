package lesson3;

import java.util.HashMap;
import java.util.Map;

public class TransliteratorImpl implements Transliterator {
    private static Map<Character, String> characterMap;

    static {
        characterMap = new HashMap<>();
        characterMap.put('А', "A");
        characterMap.put('Б', "B");
        characterMap.put('В', "V");
        characterMap.put('Г', "G");
        characterMap.put('Д', "D");
        characterMap.put('Е', "E");
        characterMap.put('Ё', "E");
        characterMap.put('Ж', "ZH");
        characterMap.put('З', "Z");
        characterMap.put('И', "I");
        characterMap.put('Й', "I");
        characterMap.put('К', "K");
        characterMap.put('Л', "L");
        characterMap.put('М', "M");
        characterMap.put('Н', "N");
        characterMap.put('О', "O");
        characterMap.put('П', "P");
        characterMap.put('Р', "R");
        characterMap.put('С', "S");
        characterMap.put('Т', "T");
        characterMap.put('У', "U");
        characterMap.put('Ф', "F");
        characterMap.put('Х', "KH");
        characterMap.put('Ц', "TS");
        characterMap.put('Ч', "CH");
        characterMap.put('Ш', "SH");
        characterMap.put('Щ', "SHCH");
        characterMap.put('Ы', "Y");
        characterMap.put('Ь', "");
        characterMap.put('Ъ', "IE");
        characterMap.put('Э', "E");
        characterMap.put('Ю', "IU");
        characterMap.put('Я', "IA");
    }

    @Override
    public String transliterate(String source) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            if (characterMap.containsKey(source.charAt(i))) {
                result.append(characterMap.get(source.charAt(i)));
            } else {
                result.append(source.charAt(i));
            }
        }
        return result.toString();
    }
}