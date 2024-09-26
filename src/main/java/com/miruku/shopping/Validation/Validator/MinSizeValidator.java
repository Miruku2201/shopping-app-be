package com.miruku.shopping.Validation.Validator;

import com.miruku.shopping.Validation.Constraint.MinSizeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinSizeValidator implements ConstraintValidator<MinSizeConstraint, String> {
    private int min;
    @Override
    public boolean isValid(String text, ConstraintValidatorContext constraintValidatorContext) {
        int textSize = text.length();
        return textSize >= min;
    }

    @Override
    public void initialize(MinSizeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }
}