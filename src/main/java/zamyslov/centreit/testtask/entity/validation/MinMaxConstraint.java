package zamyslov.centreit.testtask.entity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/***
 * Кастомное ограничение на то, что минимум не должен быть больше максимума
 */
@Documented
@Constraint(validatedBy = MinMaxValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinMaxConstraint {
    String message() default "zamyslov.centreit.testtask.entity.validation.MinMaxConstraint";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
