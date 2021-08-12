package io.twodigits.urlshortener.validation.constrains;


import io.twodigits.urlshortener.validation.validators.UserExistDBValidatorForString;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserExistDBValidatorForString.class)
public @interface UserExist {

    String message() default "{io.twodigits.urlshortener.validation.constrains.UserExist.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
