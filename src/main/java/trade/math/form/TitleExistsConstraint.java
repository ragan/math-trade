package trade.math.form;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by karol on 03.03.16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TitleExistsValidator.class)
public @interface TitleExistsConstraint {

    String message() default "title is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
