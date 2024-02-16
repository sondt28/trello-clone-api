package com.son.todolist.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.son.todolist.common.based.BaseDto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoDto extends BaseDto {
    @NotBlank(message = "text field must not be null")
    private String text;
    private int order;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime dueDate;
    @Min(1)
    @Max(10)
    private int priority;
    @NotNull(message = "sectionId field must not be null")
    private Long sectionId;
}
