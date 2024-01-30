package com.son.todolist.user;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
        @NotBlank(message = "email must not be blank")
        String email,
        @NotBlank(message = "password must not be blank")
        String password) {
}
