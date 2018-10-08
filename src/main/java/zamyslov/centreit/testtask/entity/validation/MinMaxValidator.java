package zamyslov.centreit.testtask.entity.validation;

import zamyslov.centreit.testtask.entity.WeatherIndicator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/***
 * Валидатор ограничения MinMaxConstraint
 */
public class MinMaxValidator implements ConstraintValidator<MinMaxConstraint, WeatherIndicator> {

    @Override
    public void initialize(MinMaxConstraint constraintAnnotation) {}

    @Override
    public boolean isValid(WeatherIndicator weatherIndicator, ConstraintValidatorContext context) {
        if (!weatherIndicator.hasMinValue() || !weatherIndicator.hasMaxValue() ||
                weatherIndicator.getMinValue() <= weatherIndicator.getMaxValue())
            return true;
        else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{zamyslov.centreit.testtask.entity.validation.MinMaxConstraint}")
                    .addPropertyNode("min").addConstraintViolation();
            return false;
        }
    }
}
