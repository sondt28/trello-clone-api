package com.son.todolist.user;

public interface AuthService {
    String login(UserLoginDto dto);
    void register(UserRegisterDto dto);
}
