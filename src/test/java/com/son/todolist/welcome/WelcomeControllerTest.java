package com.son.todolist.welcome;

import com.son.todolist.common.config.SecurityConfig;
import com.son.todolist.common.helper.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WelcomeController.class)
@Import(SecurityConfig.class)
public class WelcomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtHelper helper;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void welcomeTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/welcome")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to my application !"))
                .andReturn();
    }
}
