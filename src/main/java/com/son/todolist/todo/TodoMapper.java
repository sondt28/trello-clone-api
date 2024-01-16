package com.son.todolist.todo;

import com.son.todolist.user.User;
import com.son.todolist.user.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public abstract class TodoMapper {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    abstract TodoDto todoToTodoDto(Todo todo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "user", expression = "java(getUserByEmail(email))")
    abstract Todo dtoToTodo(TodoDto dto, String email);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    abstract void updateTodoFromDto(TodoDto dto, @MappingTarget Todo todo);

    public Page<TodoDto> pageTodoToPageTodoDto(Page<Todo> todoPage) {
        return todoPage.map(this::todoToTodoDto);
    }
    protected User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
