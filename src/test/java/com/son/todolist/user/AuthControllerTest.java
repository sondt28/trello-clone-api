package com.son.todolist.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.son.todolist.common.config.SecurityConfig;
import com.son.todolist.common.helper.JwtHelper;
import org.junit.jupiter.api.Test;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class})
public class AuthControllerTest {
    private static final String END_POINT_PATH = "/auth";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthService service;
    @MockBean
    private JwtHelper helper;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserRepository repository;

    @Test
    void shouldReturn400BadRequestWhenRegisterWithEmailExisted() throws Exception {
        UserRegisterDto dto = UserRegisterDto.builder()
                .email("exist@gmail.opcm")
                .password("123")
                .confirmPassword("123")
                .build();

        String requestBody = objectMapper.writeValueAsString(dto);
        when(repository.findByEmail("exist@gmail.opcm"))
                .thenReturn(Optional.of(new User()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(END_POINT_PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        ResultActions result = mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void shouldReturn400BadRequestWhenRegisterWithPasswordAndConfirmPasswordNotMatch() throws Exception {
        UserRegisterDto dto = UserRegisterDto.builder()
                .email("abc@g")
                .password("1")
                .confirmPassword("2")
                .build();

        String requestBody = objectMapper.writeValueAsString(dto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(END_POINT_PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        ResultActions result = mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void shouldReturn201WhenRegisterWithValidField() throws Exception {
        UserRegisterDto dto = UserRegisterDto.builder()
                .email("abc@g")
                .password("1")
                .confirmPassword("1")
                .build();

        String requestBody = objectMapper.writeValueAsString(dto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(END_POINT_PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        ResultActions result = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("http://*/auth/login"));
    }
}
