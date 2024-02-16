package com.son.todolist.common.validation;

import com.son.todolist.user.UserRegisterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegisterDto> {
    @Override
    public boolean isValid(UserRegisterDto obj, ConstraintValidatorContext context) {
        if (obj == null)
            return false;

        String password = obj.getPassword();
        String confirmPassword = obj.getConfirmPassword();

        return password != null && password.equals(confirmPassword);
    }
}
