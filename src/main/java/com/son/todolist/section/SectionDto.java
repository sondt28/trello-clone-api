package com.son.todolist.section;

import com.son.todolist.common.based.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionDto extends BaseDto {
        @NotBlank(message = "name must not be blank")
        private String name;
        @NotNull(message = "projectId must not be null")
        private Long projectId;
        private int order;
}
