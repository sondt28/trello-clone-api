package com.son.todolist.project;

import com.son.todolist.user.User;
import com.son.todolist.user.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.son.todolist.common.helper.Constant.CHARCOAL_COLOR;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hexColor", expression = "java(handleHexColorFormat(dto.hexColor()))")
    abstract Project dtoToProject(ProjectDto dto, User user);

    abstract ProjectDto projectToDto(Project project);

    abstract ProjectAndSectionDto projectToprojectAndSectionDto(Project project);

    @Mapping(target = "id", ignore = true)
    abstract Project updateProjectFromDto(ProjectDto dto, @MappingTarget Project project);

    public List<ProjectDto> projectsToDtos(List<Project> projects) {
        return projects.stream().map(this::projectToDto).toList();
    }

    protected String handleHexColorFormat(String hexColor) {
        return hexColor == null ? CHARCOAL_COLOR : hexColor.toLowerCase();
    }
}
