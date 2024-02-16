package com.son.todolist.project;

import com.son.todolist.section.Section;
import com.son.todolist.section.SectionRepository;
import com.son.todolist.user.User;
import com.son.todolist.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProjectUserRepositoryTest {
    @Autowired
    ProjectUserRepository projectUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    SectionRepository sectionRepository;

    User user;
    Project project;
    ProjectUser projectUser;
    Section section;

    @BeforeEach
    void beforeEach() {
        user = User.builder()
                .email("user@123")
                .password("123")
                .build();
        user = userRepository.save(user);

        project = Project.builder()
                .user(user)
                .name("Daily routine")
                .hexColor("#49e87b")
                .build();
        project = projectRepository.save(project);

        projectUser = ProjectUser.builder()
                .id(new ProjectUserId(project.getId(), user.getId()))
                .project(project)
                .user(user)
                .build();
        projectUserRepository.save(projectUser);

        section = Section.builder()
                .name("Todo")
                .order(0)
                .project(project)
                .build();
        sectionRepository.save(section);
    }

    @Test
    void shouldReturnProjectUser() {
        ProjectUserId projectUserId = new ProjectUserId(project.getId(), user.getId());
        Optional<ProjectUser> actual = projectUserRepository.findById(projectUserId);

        assertThat(actual).isPresent();
        assertThat(actual.get().getProject()).isEqualTo(project);
        assertThat(actual.get().getUser()).isEqualTo(user);
        assertThat(actual.get().getId()).isEqualTo(projectUserId);
    }

}
