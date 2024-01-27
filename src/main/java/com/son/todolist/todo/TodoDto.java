package com.son.todolist.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record TodoDto(
        Long id,
        @NotBlank(message = "text field must not be null")
        String text,
        int order,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Future
        LocalDateTime dueDate,
        @Min(1)
        @Max(10)
        int priority,
        @NotNull(message = "sectionId field must not be null")
        Long sectionId
) {
}
