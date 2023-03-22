package lesson2.snilsValidator;

public class SnilsValidatorImpl implements SnilsValidator {
    @Override
    public boolean validate(String snils) {

        if (snils == null || snils.length() != 11) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            if (!Character.isDigit(snils.charAt(i))) {
                return false;
            }
            sum += Character.digit(snils.charAt(i), 10) * (9 - i);
        }

        int testControl;
        if (sum < 100) {
            testControl = sum;
        } else if (sum == 100 || sum % 101 == 100) {
            testControl = 0;
        } else {
            testControl = sum % 101;
        }

        int control = Character.digit(snils.charAt(9), 10) * 10
                + Character.digit(snils.charAt(10), 10);

        return control == testControl;
    }
}
