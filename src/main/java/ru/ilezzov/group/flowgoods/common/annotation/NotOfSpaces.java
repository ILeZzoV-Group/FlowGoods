package ru.ilezzov.group.flowgoods.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotOfSpacesValidator.class)
@Documented
public @interface NotOfSpaces {
    String message() default "{validation.general.not_of_spaces}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
