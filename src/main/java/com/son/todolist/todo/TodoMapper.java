package com.son.todolist.todo;

import com.son.todolist.section.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TodoMapper {

    @Autowired
    private TodoRepository todoRepository;

    @Mapping(target = "sectionId", source = "todo.section.id")
    abstract TodoDto todoToTodoDto(Todo todo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "order", expression = "java(getTotalTodosOfSection(dto.getSectionId()))")
    abstract Todo dtoToTodo(TodoDto dto, Section section);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "order", ignore = true)
    abstract void updateTodoFromDto(TodoDto dto, @MappingTarget Todo todo);

    public List<TodoDto> pageTodoToPageTodoDto(List<Todo> todos) {
        return todos.stream().map(this::todoToTodoDto).toList();
    }

    protected int getTotalTodosOfSection(Long sectionId) {
        return todoRepository.countTodoBySectionId(sectionId);
    }
}
