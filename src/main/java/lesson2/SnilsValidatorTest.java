package lesson2;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        SnilsValidator validator = new SnilsValidatorImpl();
        System.out.println(validator.validate("01468870570")); //false
        System.out.println(validator.validate("90114404441")); //true
    }
}
