package com.son.todolist.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationOTPDto {
    @NotBlank(message = "otp must not be blank")
    private String otp;
}
