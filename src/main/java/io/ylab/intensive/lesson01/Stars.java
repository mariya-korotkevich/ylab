package io.ylab.intensive.lesson01;

import java.util.Scanner;

public class Stars {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();

            StringBuilder builderLine = new StringBuilder();
            for (int i = 0; i < m; i++) {
                if (builderLine.length() != 0){
                    builderLine.append(" ");
                }
                builderLine.append(template);
            }

            String line = builderLine.toString();
            for (int i = 0; i < n; i++) {
                System.out.println(line);
            }
        }
    }
}