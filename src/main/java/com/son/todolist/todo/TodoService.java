package com.son.todolist.todo;

import java.util.List;

public interface TodoService {
    TodoDto findById(Long id);
    List<TodoDto> findAllBySection(Long sectionId);
}
