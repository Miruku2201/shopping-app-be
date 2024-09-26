package com.miruku.shopping.Validation.Constraint;

import com.miruku.shopping.Validation.Validator.DobValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    String message() default "Invalid date of birth";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    // customize validated value
    int min();
}
