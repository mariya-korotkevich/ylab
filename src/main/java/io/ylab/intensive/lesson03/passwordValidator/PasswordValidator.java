package io.ylab.intensive.lesson03.passwordValidator;

public class PasswordValidator {

    public static boolean validate(String login, String password, String confirmPassword) {
        try {
            if (!login.matches("([0-9_A-Za-z]*)")) {
                throw new WorngLoginException("Логин содержит недопустимые символы");
            }
            if (login.length() >= 20) {
                throw new WorngLoginException("Логин слишком длинный");
            }
            if (!password.matches("([0-9_A-Za-z]*)")) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }
            if (password.length() >= 20) {
                throw new WrongPasswordException("Пароль слишком длинный");
            }
            if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }
        } catch (WorngLoginException | WrongPasswordException exception){
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }
}