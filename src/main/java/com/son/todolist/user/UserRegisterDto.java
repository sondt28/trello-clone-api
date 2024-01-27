package com.son.todolist.user;

import com.son.todolist.common.validation.EmailExisted;
import com.son.todolist.common.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@PasswordMatches
public record UserRegisterDto(
        @EmailExisted
        @NotBlank(message = "email must not be blank.")
        @Email(message = "email must be correct format.")
        String email,
        @NotBlank(message = "password must not be empty.")
        String password,
        @NotBlank(message = "confirmPassword must not be blank.")
        String confirmPassword) {
}
