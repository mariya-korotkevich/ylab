package lesson2;

public class RateLimitedPrinter {
    private final int interval;
    private long lastPrinted;

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
    }
    public void print(String message) {
        if (System.currentTimeMillis() - lastPrinted > interval) {
            System.out.println(message);
            lastPrinted = System.currentTimeMillis();
        }
    }
}