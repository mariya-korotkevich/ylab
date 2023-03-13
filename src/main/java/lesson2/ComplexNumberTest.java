package lesson2;

public class ComplexNumberTest {
    public static void main(String[] args) {

        System.out.println("(5 + 7i) + (5.5 + 2i) = " + new ComplexNumber(5, 7).add(
                new ComplexNumber(5.5, 2)));

        System.out.println("(-5.5 -7i) + (5.5 -2i) = " + new ComplexNumber(-5.5, -7).add(
                new ComplexNumber(5.5, -2)));

        System.out.println("12 - (16 - 2i) = " + new ComplexNumber(12).subtract(
                new ComplexNumber(16, -2)));

        System.out.println("(52 + 8i) - (16 -2i) = " + new ComplexNumber(52, 8).subtract(
                new ComplexNumber(16, -2)));

        System.out.println("(-4 -7i) - (-2 - 2i) = " + new ComplexNumber(-4, -7).subtract(
                new ComplexNumber(-2, -2)));

        System.out.println("(7 + 3i) * (5 - i) = " + new ComplexNumber(7, 3).multiply(
                new ComplexNumber(5, -1)));

        System.out.println("(5 - i) * (7 + 3i) = " + new ComplexNumber(5, -1).multiply(
                new ComplexNumber(7, 3)));

        System.out.println("(0) * (1 + i) = " + new ComplexNumber(0).multiply(
                new ComplexNumber(1, 1)));

        System.out.println("abs(5 - 7i) = " + new ComplexNumber(5, -7).abs());
        System.out.println("abs(-1) = " + new ComplexNumber(-1).abs());
    }
}