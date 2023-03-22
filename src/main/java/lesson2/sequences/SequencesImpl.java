package lesson2.sequences;

public class SequencesImpl implements Sequences{
    @Override
    public void a(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i * 2);
        }
    }

    @Override
    public void b(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(1 + 2 * (i - 1));
        }
    }

    @Override
    public void c(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println((int) Math.pow(i, 2));
        }
    }

    @Override
    public void d(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println((int) Math.pow(i, 3));
        }
    }

    @Override
    public void e(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println((int)(Math.pow(-1, i - 1)));
        }
    }

    @Override
    public void f(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i * (int)(Math.pow(-1, i - 1)));
        }
    }

    @Override
    public void g(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i * i * (int)(Math.pow(-1, i - 1)));
        }
    }

    @Override
    public void h(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println((i - i / 2) * (i % 2));
        }
    }

    @Override
    public void i(int n) {
        int number = 1;
        for (int i = 1; i <= n; i++) {
            number = number * i;
            System.out.println(number);
        }
    }

    @Override
    public void j(int n) {
        int prevNumber1 = 1;
        int prevNumber2 = 1;
        int number;
        for (int i = 1; i <= n; i++) {
            if (i == 1 || i == 2){
                System.out.println(1);
                continue;
            }
            number = prevNumber1 + prevNumber2;
            prevNumber2 = prevNumber1;
            prevNumber1 = number;
            System.out.println(number);
        }
    }
}