package trade.math.form;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by karol on 19.02.16.
 */
public class MatchesConfirmationValidator implements ConstraintValidator<MatchesConfirmation, NewTradeUserForm>{

    private String password;

    @Override
    public void initialize(MatchesConfirmation constraintAnnotation) {
    }

    @Override
    public boolean isValid(NewTradeUserForm value, ConstraintValidatorContext context) {
        return false;
    }
}
