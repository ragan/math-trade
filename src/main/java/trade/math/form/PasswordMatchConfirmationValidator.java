package trade.math.form;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by karol on 19.02.16.
 */
public class PasswordMatchConfirmationValidator implements ConstraintValidator<PasswordMatchConfirmation, NewTradeUserForm> {

    @Override
    public void initialize(PasswordMatchConfirmation constraintAnnotation) {
    }

    @Override
    public boolean isValid(NewTradeUserForm value, ConstraintValidatorContext context) {
        try {
            String password = BeanUtils.getProperty(value, "password");
            String passwordConfirmation = BeanUtils.getProperty(value, "passwordConfirmation");
            return password.equals(passwordConfirmation);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }
}
