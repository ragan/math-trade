package trade.math.form;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by karol on 19.02.16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchConfirmationValidator.class)
public @interface PasswordMatchConfirmation {

    String message() default "password does not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
