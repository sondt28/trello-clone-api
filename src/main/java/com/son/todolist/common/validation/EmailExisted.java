package com.son.todolist.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = EmailExistedValidator.class)
public @interface EmailExisted {
    String message() default "Email already exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
