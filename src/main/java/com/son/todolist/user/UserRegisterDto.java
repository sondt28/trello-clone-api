package com.son.todolist.user;

public record UserRegisterDto(
        String email,
        String password,
        String confirmPassword) {
}
