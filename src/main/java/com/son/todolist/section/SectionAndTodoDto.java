package com.son.todolist.section;

import com.son.todolist.todo.TodoDto;

import java.util.Set;

public record SectionAndTodoDto(
        Long id,
        String name,
        int order,
        Set<TodoDto> todos) {
}
