package io.ylab.intensive.lesson01;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        int number = new Random().nextInt(99) + 1; // здесь загадывается число от 1 до 99
        int maxAttempts = 10; // здесь задается количество попыток
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");

        try (Scanner scanner = new Scanner(System.in)) {
            boolean win = false;
            int attempt = 1;
            while(!win && attempt <= maxAttempts){
                int n = scanner.nextInt();
                if (n == number){
                    System.out.println("Ты угадал с " + attempt + " попытки");
                    win = true;
                } else if (n < number){
                    System.out.println("Мое число больше! У тебя осталось " + (maxAttempts - attempt) + " попыток");
                } else {
                    System.out.println("Мое число меньше! У тебя осталось " + (maxAttempts - attempt) + " попыток");
                }
                attempt++;
            }
            if (!win){
                System.out.println("Ты не угадал");
            }
        }
    }
}
