package lesson3.passwordValidator;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        System.out.println("validate(\"тест\", \"pass\", \"pass\") = "
                + PasswordValidator.validate("тест", "pass", "pass")); //false - login содержит кириллицу
        System.out.println("validate(\"veryVeryVeryLongLogin\", \"pass\", \"pass\") = "
                + PasswordValidator.validate("veryVeryVeryLongLogin", "pass", "pass")); //false - login длинней 20 символов
        System.out.println("validate(\"login\", \"пасс\", \"пасс\") = "
                + PasswordValidator.validate("login", "пасс", "пасс")); //false - password содержит кириллицу
        System.out.println("validate(\"login\", \"veryVeryVeryLongPassword\", \"veryVeryVeryLongPassword\") = "
                + PasswordValidator.validate("login", "veryVeryVeryLongPassword", "veryVeryVeryLongPassword")); //false - password длинней 20 символов
        System.out.println("validate(\"login\", \"password\", \"otherPassword\") = "
                + PasswordValidator.validate("login", "password", "otherPassword")); //false - password и confirmPassword не совпадают
        System.out.println("validate(\"login\", \"password\", \"password\") = "
                + PasswordValidator.validate("login", "password", "password")); //true - всё ок
        System.out.println("validate(\"\", \"\", \"\") = "
                + PasswordValidator.validate("", "", "")); //true - всё ок
        System.out.println("validate(\"111\", \"222\", \"222\") = "
                + PasswordValidator.validate("111", "222", "222")); //true - всё ок
        System.out.println("validate(\"___\", \"_\", \"_\") = "
                + PasswordValidator.validate("___", "_", "_")); //true - всё ок
        System.out.println("validate(\"a1_\", \"b_2\", \"b_2\") = "
                + PasswordValidator.validate("a1_", "b_2", "b_2")); //true - всё ок

    }
}