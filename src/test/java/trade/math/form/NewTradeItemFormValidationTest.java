package trade.math.form;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class NewTradeItemFormValidationTest {

    private Validator validator;

    private NewTradeItemForm newTradeItemForm;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        newTradeItemForm = getValidItemForm();

        assertThat(validator.validate(newTradeItemForm), is(empty()));
    }

    @Test
    public void testUsernameCanNotBeEmpty() throws Exception {
        newTradeItemForm.setDescription("");
        assertThat(validator.validate(newTradeItemForm), is(not(empty())));
    }

    private NewTradeItemForm getValidItemForm() {
        return new NewTradeItemForm(123, "description");
    }

}