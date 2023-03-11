package lesson2;

public class SnilsValidatorImpl implements SnilsValidator{
    @Override
    public boolean validate(String snils) {
        int k = 9;

        int sum = 0;
        int control = 0;
        for (int i = 0; i < snils.length(); i++) {
            if (k < -1){
                break;
            }
            if (!Character.isDigit(snils.charAt(i))){
                continue;
            }
            if (k <= 0){
                control += Character.digit(snils.charAt(i), 10) * (k == 0 ? 10 : 1);
            } else {
                sum += Character.digit(snils.charAt(i), 10) * k;
            }
            k--;
        }

        int control2 = 0;
        if (sum < 100){
            control2 = sum;
        } else if (sum == 100){
            control2 = 0;
        } else if (sum % 101 == 100){
            control2 = 0;
        } else {
            control2 = sum % 101;
        }

        System.out.println("sum = " + sum + "; control = " + control + "; control2 = " + control2);

        return control == control2;
    }
}
