package io.ylab.intensive.lesson03.passwordValidator;

public class WorngLoginException extends Exception{
    public WorngLoginException() {
    }

    public WorngLoginException(String message) {
        super(message);
    }
}
