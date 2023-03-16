package lesson2;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        SnilsValidator validator = new SnilsValidatorImpl();
        System.out.println("validate(null) = " + validator.validate(null)); //false
        System.out.println("validate(\"\") = " + validator.validate("")); //false
        System.out.println("validate(\"test\") = " + validator.validate("test")); //false
        System.out.println("validate(\"05137042723\") = " + validator.validate("05137042723")); //false
        System.out.println("validate(\"01468870570\") = " + validator.validate("01468870570")); //false
        System.out.println("validate(\"90114404441\") = " + validator.validate("90114404441")); //true
        System.out.println("validate(\"05137042722\") = " + validator.validate("05137042722")); //true
        System.out.println("validate(\"16457378098\") = " + validator.validate("16457378098")); //true
        System.out.println("validate(\"18513691788\") = " + validator.validate("18513691788")); //true
    }
}
