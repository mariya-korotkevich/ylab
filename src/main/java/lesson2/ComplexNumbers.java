package lesson2;

public class ComplexNumbers {
    private double a;
    private double b;

    public ComplexNumbers(double a) {
        this.a = a;
    }

    public ComplexNumbers(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public ComplexNumbers sum (ComplexNumbers number){
        return null;
    }

    public ComplexNumbers minus (ComplexNumbers number){
        return null;
    }

    public ComplexNumbers umnoj(ComplexNumbers number){
        return null;
    }
    public ComplexNumbers abs(){
        return null;
    }

    @Override
    public String toString() {
        String result;
        if (b == 0){
            result = "" + a;
        } else if (a == 0){
            result = b + "i";
        } else {
            result = a + " + " + b + "i";
        }
        return result;
    }
}
