package com.son.todolist.project;

import com.son.todolist.common.based.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDto extends BaseDto {
        @NotBlank(message = "Name must not be blank")
        private String name;
        @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Invalid color code. Please use the format #RRGGBB")
        private String hexColor;
        private boolean isPublic;
}
