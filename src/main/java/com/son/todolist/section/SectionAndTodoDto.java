package com.son.todolist.section;

import com.son.todolist.todo.TodoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class SectionAndTodoDto extends SectionDto {
    private Set<TodoDto> todos;
}
