package io.ylab.intensive.lesson03.fileSort;

import java.io.File;
import java.io.IOException;

public class SorterTest {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("data.txt", 1000);
        System.out.println(new Validator(dataFile).isSorted()); // false
        File sortedFile = new Sorter(100).sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); // true
    }
}