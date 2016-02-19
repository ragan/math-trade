package trade.math.form;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by karol on 18.02.16.
 */
public class NewTradeUserFormValidationTest extends TestCase {

    private Validator validator;
    private NewTradeUserForm newTradeUserForm;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        newTradeUserForm = getValidUserForm();

        assertTrue(validator.validate(newTradeUserForm).isEmpty());
    }

    @Test
    public void testUsernameCanNotBeEmpty() throws Exception {
        newTradeUserForm.setUsername("");
        assertTrue(!validator.validate(newTradeUserForm).isEmpty());
    }

    @Test
    public void testInvalidUserNames() throws Exception {
        assertUsernameInvalid("user name");
        assertUsernameInvalid("user@name");
        assertUsernameInvalid("username?");
        assertUsernameInvalid("?username");
        assertUsernameInvalid("");
        assertUsernameInvalid("thisUsernameIsWayToLongToBeValidated");
    }

    @Test
    public void testValidUserNames() throws Exception {
        assertUsernameValid("username");
        assertUsernameValid("use");
        assertUsernameValid("thisUsernameIsNotTooLong");
        assertUsernameValid("userNaMe32");
        assertUsernameValid("user23NaMe");
        assertUsernameValid("user-na_3_2_me-");
    }

    @Test
    public void testEmailMustBeValid() throws Exception {
        assertEmailValid("a@b.c", true);
        assertEmailValid("a@b", true);

        assertEmailValid("a@b,c", false);
        assertEmailValid("a@", false);
        assertEmailValid("a", false);
    }

    private void assertEmailValid(String email, boolean valid) {
        newTradeUserForm.setEmail(email);
        assertTrue(validator.validate(newTradeUserForm).isEmpty() == valid);
    }

    @Test
    public void testPasswordMustNotBeEmpty() throws Exception {
        newTradeUserForm.setPassword("");
        assertFalse(validator.validate(newTradeUserForm).isEmpty());
        newTradeUserForm.setPassword("password123");
        newTradeUserForm.setPasswordConfirmation("password123");
        assertTrue(validator.validate(newTradeUserForm).isEmpty());
    }

    public void testPasswordConfirmationMustBeSameAsPassword() throws Exception {
        newTradeUserForm.setPassword("password");
        newTradeUserForm.setPasswordConfirmation("somethingElse");
        assertFalse(validator.validate(newTradeUserForm).isEmpty());

        newTradeUserForm.setPasswordConfirmation("password");
        assertTrue(validator.validate(newTradeUserForm).isEmpty());
    }

    private void assertUsernameInvalid(String username) {
        newTradeUserForm.setUsername(username);
        assertTrue(!validator.validate(newTradeUserForm).isEmpty());
    }

    private void assertUsernameValid(String username) {
        newTradeUserForm.setUsername(username);
        assertTrue(validator.validate(newTradeUserForm).isEmpty());
    }

    private NewTradeUserForm getValidUserForm() {
        NewTradeUserForm newTradeUserForm = new NewTradeUserForm();
        newTradeUserForm.setUsername("username");
        newTradeUserForm.setEmail("some@email.com");
        newTradeUserForm.setPassword("password");
        newTradeUserForm.setPasswordConfirmation("password");
        return newTradeUserForm;
    }
}