package com.son.todolist.project;

import com.son.todolist.section.SectionAndTodoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProjectAndSectionDto extends ProjectDto {
    private Set<SectionAndTodoDto> sections;
}
