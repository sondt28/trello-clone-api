package com.son.todolist.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record TodoDto(
        Long id,
        @NotBlank
        @NotEmpty
        String text,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createAt,
        boolean isCompleted,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Future
        LocalDateTime dueDate,
        @Min(1)
        @Max(10)
        int priority
) {
}
