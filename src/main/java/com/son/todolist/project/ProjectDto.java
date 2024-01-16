package com.son.todolist.project;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProjectDto(
        Long id,
        @NotNull(message = "name must not be null")
        @NotEmpty(message = "name must not be empty")
        String name,
        String hexColor,
        int order,
        boolean isPublic) {
}
