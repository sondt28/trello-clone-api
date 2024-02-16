package com.son.todolist.section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SectionController.class)
@AutoConfigureMockMvc
public class SectionControllerTest {
    @Autowired
    MockMvc mockMvc;
}
