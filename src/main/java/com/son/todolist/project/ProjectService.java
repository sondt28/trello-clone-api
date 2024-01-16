package com.son.todolist.project;

import com.son.todolist.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.son.todolist.helper.Constant.PROJECT_NOT_FOUND_MESSAGE;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;

    public List<ProjectDto> getAll(String email) {
        List<Project> projects = projectRepository.findByUserEmailOrderByOrderAsc(email);

        return mapper.projectsToDtos(projects);
    }

    public ProjectDto get(Long id, String email) {
        Project project = projectRepository.findByIdAndUserEmail(id, email);

        if (project == null)
            return null;

        return mapper.projectToDto(project);
    }

    public ProjectDto save(ProjectDto dto, String email) {
        Project project = mapper.dtoToProject(dto, email);

        project = projectRepository.save(project);
        return mapper.projectToDto(project);
    }

    public void update(Long id, String email, ProjectDto dto) {
        Project project = getProjectByUserEmail(id, email);
        if (project == null)
            throw new ProjectException(PROJECT_NOT_FOUND_MESSAGE);

        project = mapper.updateProjectFromDto(dto, project);
        projectRepository.save(project);
    }

    public void delete(Long id, String email) {
        Project project = getProjectByUserEmail(id, email);

        if (project == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        projectRepository.delete(project);
    }

    private Project getProjectByUserEmail(Long id, String email) {
        return projectRepository.findByIdAndUserEmail(id, email);
    }

    public void moving(Long projectId, String email, int newOrder) {
        Project project = getProjectByUserEmail(projectId, email);

        if (project == null)
            throw new ProjectException(PROJECT_NOT_FOUND_MESSAGE);

        int oldOrder = project.getOrder();
        if (newOrder > oldOrder) {
            projectRepository.decrementAboveToPosition(newOrder, oldOrder, email);
        } else if (newOrder < oldOrder){
            projectRepository.incrementBelowToPosition(newOrder, oldOrder, email);
        } else {
            throw new ProjectException("Invalid order");
        }

        project.setOrder(newOrder);
        projectRepository.save(project);
    }
}
