package com.son.todolist.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.son.todolist.common.config.SecurityConfig;
import com.son.todolist.common.helper.JwtHelper;
import com.son.todolist.common.validation.EmailExistedValidator;
import com.son.todolist.welcome.WelcomeController;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class,  EmailExistedValidator.class})
public class AuthControllerTest {
    private static final String END_POINT_PATH = "/auth";

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private AuthService service;
    @MockBean private JwtHelper helper;
    @MockBean private UserDetailsService userDetailsService;
    @MockBean private UserRepository repository;

    @Test
    void shouldReturn400BadRequestWhenRegisterWithEmailExisted() throws Exception {
        UserRegisterDto dto = new UserRegisterDto("existing@gmail.com", "123", "123");
        String requestBody = objectMapper.writeValueAsString(dto);

        Mockito.when(repository.findByEmail("existing@gmail.com"))
                .thenReturn(Optional.of(new User()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(END_POINT_PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        ResultActions result = mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
