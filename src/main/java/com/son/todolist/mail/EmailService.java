package com.son.todolist.mail;

public interface EmailService {
    void sendVerificationToken(String otp, String email);
}
