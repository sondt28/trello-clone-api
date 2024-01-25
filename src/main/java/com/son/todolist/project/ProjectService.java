package com.son.todolist.project;

import com.son.todolist.common.exception.NotFoundException;
import com.son.todolist.user.User;
import com.son.todolist.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;
    private final ProjectMapper mapper;

    public List<ProjectDto> getAll(String email) {
        List<Project> projects = projectUserRepository.findProjectByUserEmail(email);

        return mapper.projectsToDtos(projects);
    }

    public ProjectAndSectionDto get(Long id, String email) {
        Project project = getProjectByIdAndUserEmail(id, email);

        return mapper.projectToprojectAndSectionDto(project);
    }

    @Transactional
    public ProjectDto save(ProjectDto dto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));

        Project project = mapper.dtoToProject(dto, user);

        project = projectRepository.save(project);
        ProjectUserId projectUserId = new ProjectUserId(project.getId(), user.getId());
        ProjectUser projectUser = new ProjectUser(projectUserId, project, user);

        projectUserRepository.save(projectUser);

        return mapper.projectToDto(project);
    }

    public void update(Long id, String email, ProjectDto dto) {
        Project project = getProjectByIdAndUserEmail(id, email);

        project = mapper.updateProjectFromDto(dto, project);
        projectRepository.save(project);
    }

    public void delete(Long id, String email) {
        Project project = getProjectByIdAndUserEmail(id, email);
        projectRepository.delete(project);
    }

    public void moving(Long projectId, String email, int newOrder) {
        Project project = getProjectByIdAndUserEmail(projectId, email);

        int totalProject = projectRepository.countProjectByUserEmail(email);

        int oldOrder = project.getOrder();
        if (newOrder > oldOrder && newOrder <= totalProject - 1) {
            projectRepository.decrementAboveToPosition(newOrder, oldOrder, email);
        } else if (newOrder < oldOrder && newOrder >= 0) {
            projectRepository.incrementPrevToPosition(newOrder, oldOrder, email);
        } else {
            throw new NotFoundException("Invalid order");
        }

        project.setOrder(newOrder);
        projectRepository.save(project);
    }

    private Project getProjectByIdAndUserEmail(Long id, String email) {
        return projectRepository.findProjectWithSortedSections(id, email)
                .orElseThrow(() -> new NotFoundException("Project not found."));
    }

    public void addUserToProject(Long id, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));

        ProjectUserId projectUserId = new ProjectUserId(id, userId);

        Optional<ProjectUser> projectUserOpt = projectUserRepository.findById(projectUserId);

        if (projectUserOpt.isEmpty()) {
            ProjectUser projectUser = new ProjectUser(projectUserId, project, user);
            projectUserRepository.save(projectUser);
        }
    }

    public void removeUserFromProject(Long id, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));

        ProjectUserId projectUserId = new ProjectUserId(id, userId);

        Optional<ProjectUser> projectUserOpt = projectUserRepository.findById(projectUserId);
        projectUserOpt.ifPresent(projectUserRepository::delete);
    }
}
