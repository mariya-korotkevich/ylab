package lesson2;

public class ComplexNumberTest {
    public static void main(String[] args) {

        System.out.println(new ComplexNumber(0).add(
                new ComplexNumber(0)));

        System.out.println(new ComplexNumber(5, 7).add(
                new ComplexNumber(5.5, 2)));

        System.out.println(new ComplexNumber(-5.5, -7).add(
                new ComplexNumber(5.5, -2)));

        System.out.println(new ComplexNumber(12).subtract(
                new ComplexNumber(16, -2)));

        System.out.println(new ComplexNumber(52, 8).subtract(
                new ComplexNumber(16, -2)));

        System.out.println(new ComplexNumber(-4, -7).subtract(
                new ComplexNumber(-2, -2)));

        System.out.println(new ComplexNumber(7, 3).multiply(
                new ComplexNumber(5, -1)));

        System.out.println(new ComplexNumber(5, -1).multiply(
                new ComplexNumber(7, 3)));

        System.out.println(new ComplexNumber(5, -7).abs());
        System.out.println(new ComplexNumber(-1).abs());
    }
}