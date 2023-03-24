package io.ylab.intensive.lesson02.rateLimitedPrinter;

import java.util.Date;

public class RateLimitedPrinterTest {
    public static void main(String[] args) {
        RateLimitedPrinter rateLimitedPrinter = new RateLimitedPrinter(3000);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter.print(new Date().toString());
        }
    }
}
