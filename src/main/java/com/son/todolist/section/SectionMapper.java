package com.son.todolist.section;

import com.son.todolist.project.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SectionMapper {
    @Autowired
    private SectionRepository sectionRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", expression = "java(getTotalSectionInProject(dto.projectId()))")
    @Mapping(target = "name", source = "dto.name")
    abstract Section dtoToSection(SectionDto dto, Project project);

    @Mapping(target = "projectId", source = "section.project.id")
    abstract SectionDto sectionToDto(Section section);

    protected int getTotalSectionInProject(Long projectId) {
        return sectionRepository.countSectionByProjectId(projectId);
    }
}
