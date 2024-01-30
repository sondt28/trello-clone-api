package com.son.todolist.project;

import com.son.todolist.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProjectUserRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProjectUserRepository repository;

    @Test
    void simpleTest() {
        List<ProjectUser> projects = repository.findAll();

        assertThat(projects).isEmpty();
    }

    @Test
    void shouldReturnProjectFromProjectUserByUserEmail() {
        List<Project> projects = repository.findProjectsByUserEmail("jane@example.com");

        assertThat(projects.size()).isEqualTo(4);
    }
}
