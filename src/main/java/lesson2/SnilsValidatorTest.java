package lesson2;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        SnilsValidator validator = new SnilsValidatorImpl();
        System.out.println(validator.validate("")); //false
        System.out.println(validator.validate("test")); //false
        System.out.println(validator.validate("05137042723")); //false
        System.out.println(validator.validate("01468870570")); //false
        System.out.println(validator.validate("90114404441")); //true
        System.out.println(validator.validate("05137042722")); //true
        System.out.println(validator.validate("16457378098")); //true
        System.out.println(validator.validate("18513691788")); //true
    }
}
