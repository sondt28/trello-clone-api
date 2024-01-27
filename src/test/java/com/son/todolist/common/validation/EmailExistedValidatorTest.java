package com.son.todolist.common.validation;

import com.son.todolist.user.User;
import com.son.todolist.user.UserRepository;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmailExistedValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmailExistedValidator emailExistedValidator;

    @Test
    void isValidShouldReturnFalseWhenEmailExists() {
        String email = "existing@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        assertFalse(emailExistedValidator.isValid(email, any(ConstraintValidatorContext.class)));
    }

    @Test
    void isValidShouldReturnTrueWhenEmailNotExists() {
        String email = "notexisting@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertTrue(emailExistedValidator.isValid(email, any(ConstraintValidatorContext.class)));
    }
}
