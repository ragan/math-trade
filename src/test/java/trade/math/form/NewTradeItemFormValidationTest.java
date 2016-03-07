//package trade.math.form;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import trade.math.MtApplication;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//
//import java.util.Set;
//
//import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.assertThat;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = MtApplication.class)
//@ActiveProfiles("test")
//public class NewTradeItemFormValidationTest {
//
//    @Autowired
//    private Validator validator;
//
//    private NewTradeItemForm newTradeItemForm;
//
//    @Before
//    public void setUp() throws Exception {
////        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
////        validator = validatorFactory.getValidator();
//
//        newTradeItemForm = getValidItemForm();
//
//        Set<ConstraintViolation<NewTradeItemForm>> violations = validator.validate(newTradeItemForm);
//        assertThat(violations, is(empty()));
//    }
//
//    @Test
//    public void testUsernameCanNotBeEmpty() throws Exception {
//        newTradeItemForm.setDescription("");
//        assertThat(validator.validate(newTradeItemForm), is(not(empty())));
//    }
//
//    @Test
//    public void testTitleCanNotBeEmpty() throws Exception {
//        newTradeItemForm.setTitle("");
//        assertThat(validator.validate(newTradeItemForm), is(not(empty())));
//    }
//
//    @Test
//    public void testImageUrlCanBeEmpty() throws Exception {
//        newTradeItemForm.setImageUrl("");
//        assertThat(validator.validate(newTradeItemForm), is(empty()));
//    }
//
//    private NewTradeItemForm getValidItemForm() {
//        return new NewTradeItemForm("title", "description", "//");
//    }
//
//}