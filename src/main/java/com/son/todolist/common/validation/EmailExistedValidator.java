package com.son.todolist.common.validation;

import com.son.todolist.user.User;
import com.son.todolist.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class EmailExistedValidator implements ConstraintValidator<EmailExisted, String> {

    @Autowired
    private UserRepository repository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Optional<User> user = repository.findByEmail(email);

        return user.isEmpty();
    }
}
