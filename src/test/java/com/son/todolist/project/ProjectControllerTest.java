package com.son.todolist.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {
    private static final String END_POINT_PATH = "/projects";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService service;

    @Test
    @WithMockUser(username = "le")
    public void shouldReturnOneProjectWithoutSectionsByCurrentUser() throws Exception {
        when(service.get(1L, "le")).thenReturn(
                new ProjectAndSectionDto(1L, "DailyRoutine", "#808080", false, null));

        RequestBuilder request = MockMvcRequestBuilders
                .get(END_POINT_PATH + "/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{id:1, name:\"DailyRoutine\", hexColor:\"#808080\", isPublic: false, sections: null}"))
                .andReturn();
    }
}
