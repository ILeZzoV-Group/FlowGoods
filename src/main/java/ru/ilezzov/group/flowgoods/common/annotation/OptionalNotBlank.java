package ru.ilezzov.group.flowgoods.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { OptionalNotBlankValidator.class })
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalNotBlank {
    String message() default "{validation.general.optional_not_blank}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
