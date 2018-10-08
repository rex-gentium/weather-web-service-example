package zamyslov.centreit.testtask.entity.validation;

import zamyslov.centreit.testtask.entity.Permissions;
import zamyslov.centreit.testtask.entity.WeatherEntry;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WritePermissionValidator implements ConstraintValidator<WritePermissionConstraint, WeatherEntry> {
    @Override
    public void initialize(WritePermissionConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(WeatherEntry weatherEntry, ConstraintValidatorContext context) {
        int userPermissions = weatherEntry.getUser().getRole().getPermissions();
        if (Permissions.hasPermission(Permissions.WRITE, userPermissions))
            return true;
        else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{zamyslov.centreit.testtask.entity.validation.WritePermissionConstraint}")
                    .addPropertyNode("user").addConstraintViolation();
            return false;
        }
    }
}
