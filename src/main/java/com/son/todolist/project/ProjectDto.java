package com.son.todolist.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProjectDto(
        Long id,
        @NotBlank(message = "name must not be blank")
        String name,
        @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Invalid color code. Please use the format #RRGGBB")
        String hexColor,
        boolean isPublic) {
}
