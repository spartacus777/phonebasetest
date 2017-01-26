package phonebase.android.kizema.phonebasetestapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by A.Kizema on 21.11.2016.
 */

public class PhoneValidator {
    private static final String PASSWORD_PATTERN =
            "[0-9]+";

    private static PhoneValidator instance;

    private Pattern pattern;
    private Matcher matcher;

    private PhoneValidator(){
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public static PhoneValidator getInstance(){
        if (instance == null){
            instance = new PhoneValidator();
        }

        return instance;
    }

    /**
     * Validate password with regular expression
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(final String password){

        matcher = pattern.matcher(password);
        return matcher.matches();
//        return true;
    }

}
