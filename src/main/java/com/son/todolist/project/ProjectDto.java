package com.son.todolist.project;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ProjectDto(
        Long id,
        @NotNull(message = "name must not be null")
        @NotEmpty(message = "name must not be empty")
        String name,
        @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Invalid color code. Please use the format #RRGGBB")
        String hexColor,
        int order,
        boolean isPublic) {
}
