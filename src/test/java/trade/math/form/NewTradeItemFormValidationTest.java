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

    @Test
    public void testTitleCanNotBeEmpty() throws Exception {
        newTradeItemForm.setTitle("");
        assertThat(validator.validate(newTradeItemForm), is(not(empty())));
    }

    @Test
    public void testImageUrlCanBeEmpty() throws Exception {
        newTradeItemForm.setImageUrl("");
        assertThat(validator.validate(newTradeItemForm), is(empty()));
    }

    private NewTradeItemForm getValidItemForm() {
        return new NewTradeItemForm("title", "description", "//");
    }

}