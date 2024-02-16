package com.son.todolist.mail;

import com.son.todolist.common.helper.TokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendVerificationToken(String otp, String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("son286202@gmail.com");
            message.setTo(email);
            message.setSubject("Register for Trello Clone [" + otp + "]");
            message.setText("Please use the verification code to complete the registration: " + otp);
            javaMailSender.send(message);

        } catch (MailException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
