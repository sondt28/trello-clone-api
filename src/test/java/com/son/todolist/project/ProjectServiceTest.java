package com.son.todolist.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @InjectMocks
    ProjectService projectService;

    @Mock
    ProjectRepository repository;

    @Test
    void injectProjectRepositoryDependency() {
        assertNotNull(repository);
    }
}
