package io.twodigits.urlshortener.validation.validators;

import io.twodigits.urlshortener.validation.constrains.UserExist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExistDBValidatorForString implements  ConstraintValidator <UserExist, String>{

    @Override
    public void initialize(UserExist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return true; //todo Check User exist
    }
}
