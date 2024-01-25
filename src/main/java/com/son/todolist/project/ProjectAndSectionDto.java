package com.son.todolist.project;

import com.son.todolist.section.SectionAndTodoDto;

import java.util.Set;

public record ProjectAndSectionDto(
        Long id,
        String name,
        String hexColor,
        boolean isPublic,
        Set<SectionAndTodoDto> sections) {
}
