import java.util.Scanner;

public class Pell {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            System.out.println(pellNumber(n));
        }
    }

    private static long pellNumber(int n){
        if (n == 0 || n == 1) {
            return n;
        }
        long n1 = 1;
        long n2 = 0;
        long result = 0;
        for (int i = 2; i <= n; i++) {
            result = 2 * n1 + n2;
            n2 = n1;
            n1 = result;
        }
        return result;
    }
}
