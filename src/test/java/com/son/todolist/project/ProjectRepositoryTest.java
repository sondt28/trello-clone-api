package com.son.todolist.project;

import com.son.todolist.section.Section;
import com.son.todolist.section.SectionRepository;
import com.son.todolist.user.User;
import com.son.todolist.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProjectRepositoryTest {
    @Autowired
    ProjectUserRepository projectUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    TestEntityManager testEntityManager;

    User user;
    Project project;
    ProjectUser projectUser;
    Section section;

    @BeforeEach
    void beforeEach() {
        user = User.builder().email("user@123").password("123").build();
        user = userRepository.save(user);

        project = Project.builder().user(user).name("Daily routine").hexColor("#49e87b").build();
        project = projectRepository.save(project);

        projectUser = ProjectUser.builder().id(new ProjectUserId(project.getId(), user.getId())).project(project).user(user).build();
        projectUserRepository.save(projectUser);

        section = Section.builder().name("Todo").order(0).project(project).build();
        sectionRepository.save(section);
    }

    @Test
    void shouldReturnProjectWithSectionsASC() {
        Optional<Project> actualProject = projectRepository.findProjectWithSortedSections(project.getId());

        assertThat(actualProject).isPresent();
    }

    @Test
    void shouldDeleteProjectAndSection() {
        projectRepository.delete(project);

        Optional<Project> deletedProject = projectRepository.findById(project.getId());
        assertThat(deletedProject).isNotPresent();

        // Check if the associated section is deleted
        Optional<Section> deletedSection = sectionRepository.findById(section.getId());
        assertThat(deletedSection).isNotPresent();
    }
}
