package io.ylab.intensive.lesson02.complexNumbers;

public class ComplexNumber {
    private final double realPart;
    private final double imaginaryPart;

    public ComplexNumber(double realPart) {
        this.realPart = realPart;
        this.imaginaryPart = 0;
    }

    public ComplexNumber(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public double getRealPart() {
        return realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    public ComplexNumber add(ComplexNumber number) {
        return new ComplexNumber(this.realPart + number.getRealPart(),
                this.imaginaryPart + number.getImaginaryPart());
    }

    public ComplexNumber subtract(ComplexNumber number) {
        return new ComplexNumber(this.realPart - number.getRealPart(),
                this.imaginaryPart - number.getImaginaryPart());
    }

    public ComplexNumber multiply(ComplexNumber number) {
        return new ComplexNumber(this.realPart * number.getRealPart() - imaginaryPart * number.getImaginaryPart(),
                realPart * number.getImaginaryPart() + imaginaryPart * number.getRealPart());
    }

    public double abs() {
        return Math.sqrt(realPart * realPart + imaginaryPart * imaginaryPart);
    }

    @Override
    public String toString() {
        String result;
        if (imaginaryPart == 0) {
            result = "" + realPart;
        } else if (realPart == 0) {
            result = imaginaryPart + "i";
        } else {
            result = realPart + ((imaginaryPart < 0) ? "" : " + ") + imaginaryPart + "i";
        }
        return result;
    }
}