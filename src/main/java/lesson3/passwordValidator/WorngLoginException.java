package lesson3.passwordValidator;

public class WorngLoginException extends Exception{
    public WorngLoginException() {
    }

    public WorngLoginException(String message) {
        super(message);
    }
}
