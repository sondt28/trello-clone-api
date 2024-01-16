package com.son.todolist.section;

import com.son.todolist.project.Project;
import com.son.todolist.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
@Mapper(componentModel = "spring")
public abstract class SectionMapper {
    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", expression = "java(getTotalSectionInProject(dto.projectId()))")
    @Mapping(target = "name", source = "dto.name")
    abstract Section dtoToSection(SectionDto dto, Project project, String email);

    abstract SectionDto sectionToDto(Section section);

    protected int getTotalSectionInProject(Long projectId) {
        return sectionRepository.countSectionByProjectId(projectId);
    }
}
