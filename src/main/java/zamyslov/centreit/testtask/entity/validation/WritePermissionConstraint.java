package zamyslov.centreit.testtask.entity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/***
 * Кастомное ограничение на наличие прав записи у пользователя, вносящего запись о погоде
 */
@Documented
@Constraint(validatedBy = WritePermissionValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface WritePermissionConstraint {
    String message() default "zamyslov.centreit.testtask.entity.validation.WritePermissionConstraint";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
