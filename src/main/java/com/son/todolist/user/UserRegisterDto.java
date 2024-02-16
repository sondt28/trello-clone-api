package com.son.todolist.user;

import com.son.todolist.common.validation.EmailExisted;
import com.son.todolist.common.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
public class UserRegisterDto {
        @EmailExisted
        @NotBlank(message = "email must not be blank.")
        @Email(message = "email must be correct format eg: abc@xyz ")
        private String email;
        @NotBlank(message = "password must not be empty.")
        private String password;
        @NotBlank(message = "confirm password must not be blank.")
        private String confirmPassword;
}
