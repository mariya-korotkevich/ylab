package lesson2;

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
            System.out.println(i * i);
        }
    }

    @Override
    public void d(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i * i * i);
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
        int f = 1;
        for (int i = 1; i <= n; i++) {
            f = f * i;
            System.out.println(f);
        }
    }

    @Override
    public void j(int n) {
        int n1 = 1;
        int n2 = 1;
        int f = 0;
        for (int i = 1; i <= n; i++) {
            if (i == 1 || i == 2){
                System.out.println(1);
                continue;
            }
            f = n1 + n2;
            n2 = n1;
            n1 = f;
            System.out.println(f);
        }
    }
}
