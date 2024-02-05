package com.son.todolist.project;

import com.son.todolist.common.config.SecurityConfig;
import com.son.todolist.common.helper.JwtHelper;
import com.son.todolist.projectuser.ProjectUserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(ProjectControllerTest.class)
public class ProjectControllerTest {
    private static final String END_POINT_PATH = "/projects";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService service;

    @MockBean
    private ProjectUserRepository projectUserRepository;

    @MockBean
    private JwtHelper jwtHelper;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser
    public void shouldReturnOneProjectWithoutSectionsByCurrentUser() throws Exception {
        ProjectAndSectionDto dto = new ProjectAndSectionDto(1L, "DailyRoutine", "#808080", false, null);
        when(service.get(anyLong())).thenReturn(dto);

        RequestBuilder request = MockMvcRequestBuilders
                .get(END_POINT_PATH + "/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON);

         mockMvc.perform(request)
                .andExpect(status().isOk());
    }
}
