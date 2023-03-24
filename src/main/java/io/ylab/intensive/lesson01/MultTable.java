package io.ylab.intensive.lesson01;

public class MultTable {
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                System.out.printf("%d x %d = %d" + System.lineSeparator(), i, j, i * j);
            }
        }
    }
}