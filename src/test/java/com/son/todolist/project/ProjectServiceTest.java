package com.son.todolist.project;

import com.son.todolist.projectuser.ProjectUserRepository;
import com.son.todolist.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository repository;

    @Mock
    private ProjectUserRepository projectUserRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectMapper mapper;

    List<Project> projects = new ArrayList<>();

    @BeforeEach
    void setup() {
        projects = List.of(
                Project.builder().name("project 1").hexColor("#32a852").sections(null).isPublic(false).build(),
                Project.builder().name("project 2").hexColor("#3273a8").sections(null).isPublic(false).build()
        );
    }

    @Test
    void shouldReturnListProjectDtoOfUser() {
        when(projectUserRepository.findProjectsByUserEmail()).thenReturn(projects);

        List<ProjectDto> dtos = projectService.getAll();
        assertNotNull(dtos);
    }
}
