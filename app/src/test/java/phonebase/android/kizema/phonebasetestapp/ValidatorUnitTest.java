package phonebase.android.kizema.phonebasetestapp;

import org.junit.Test;

import phonebase.android.kizema.phonebasetestapp.validator.EmailValidator;
import phonebase.android.kizema.phonebasetestapp.validator.NameValidator;
import phonebase.android.kizema.phonebasetestapp.validator.PhoneValidator;
import phonebase.android.kizema.phonebasetestapp.validator.StringValidator;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ValidatorUnitTest {
    @Test
    public void name_test() throws Exception {
        NameValidator validator = NameValidator.getInstance();

        assertEquals(validator.validate(""), false);

        assertEquals(validator.validate("Name"), true);

        assertEquals(validator.validate("jkad37dh2"), true);

        assertEquals(validator.validate("jkad37 dnwe edew"), true);

        assertEquals(validator.validate("jkad37dh2djwendwe dewdwekdnwedewnndwedwe dwenwdew"), false);
    }

    @Test
    public void email_test() throws Exception {
        EmailValidator validator = EmailValidator.getInstance();

        assertEquals(validator.validate(""), false);

        assertEquals(validator.validate("Name"), false);

        assertEquals(validator.validate("jkad37dh2@"), false);

        assertEquals(validator.validate("@ndejnwdw"), false);

        assertEquals(validator.validate("jkad37dh2@dklnwedew"), false);

        assertEquals(validator.validate("jkad37dh2@nwedejwk.com"), true);

        assertEquals(validator.validate("@djenwd.com"), false);

        assertEquals(validator.validate("jkad37 dnwe edew@tr.com"), false);

        assertEquals(validator.validate("jkad37dh2djwendwe dewdwekdnwedewnndwedwe dwenwdew@dewdewd.com"), false);
    }

    @Test
    public void phone_test() throws Exception {
        PhoneValidator validator = PhoneValidator.getInstance();

        assertEquals(validator.validate(""), false);

        assertEquals(validator.validate("Name"), false);

        assertEquals(validator.validate("jdnejwkbdkwedjwe"), false);

        assertEquals(validator.validate("jkad37 324 43543"), false);

        assertEquals(validator.validate("21832732732"), true);

        assertEquals(validator.validate("1"), true);

        assertEquals(validator.validate("13240328434284237483289497328947"), true);

        assertEquals(validator.validate("djw2oe"), false);

        assertEquals(validator.validate("2 2"), false);
    }

    @Test
    public void stringvalidator_test() throws Exception {
        StringValidator validator = StringValidator.getInstance();

        assertEquals(validator.validate(""), false);

        assertEquals(validator.validate("N"), true);

        assertEquals(validator.validate("jdnejwkbdkwedjwe"), true);

        assertEquals(validator.validate("jkad37 dnwe edew"), true);

        assertEquals(validator.validate("21832732732"), true);

        assertEquals(validator.validate("djw2oe"), true);

        assertEquals(validator.validate("jkad37dh2djwendwe dewdwekdnwedewnndwedwe dwenwdew"), true);
    }

}