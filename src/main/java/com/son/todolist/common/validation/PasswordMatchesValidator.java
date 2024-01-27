package com.son.todolist.common.validation;

import com.son.todolist.user.UserRegisterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegisterDto> {
    @Override
    public boolean isValid(UserRegisterDto obj, ConstraintValidatorContext context) {
        if (obj == null)
            return false;

        String password = obj.password();
        String confirmPassword = obj.confirmPassword();

        return password != null && password.equals(confirmPassword);
    }
}
