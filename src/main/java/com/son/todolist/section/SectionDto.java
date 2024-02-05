package com.son.todolist.section;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SectionDto(
        Long id,
        @NotBlank(message = "name must not be blank")
        String name,
        @NotNull(message = "projectId must not be null")
        Long projectId,
        int order) {
}
