package com.son.todolist.project;

import com.son.todolist.projectuser.ProjectUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProjectUserRepositoryTest {

    @Autowired
    private ProjectUserRepository repository;
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void testFindProjectByUserEmail() {

    }
}
