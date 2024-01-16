package com.son.todolist.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository repository;
    private final TodoMapper todoMapper;

    public TodoDto findById(Long id) {
        Todo todo = findTodoById(id);
        if (todo != null)
            return todoMapper.todoToTodoDto(todo);

        return null;
    }

    public TodoDto findByOwner(Long id, String email) {
        Todo todo = findTodoById(id, email);
        if (todo != null)
            return todoMapper.todoToTodoDto(todo);

        return null;
    }

    public Page<TodoDto> findByUser(Pageable pageable, String email) {
        Page<Todo> todoPage =  repository.findByUserEmail(email,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.DESC, "priority"))));

        return todoMapper.pageTodoToPageTodoDto(todoPage);
    }

    public Page<Todo> findAll(Pageable pageable) {
        return repository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.DESC, "priority"))
                )
        );
    }

    public TodoDto save(TodoDto dto, String email) {
        Todo todo = todoMapper.dtoToTodo(dto, email);
        Todo savedTodo = repository.save(todo);

        return todoMapper.todoToTodoDto(savedTodo);
    }

    public boolean update(Long id, TodoDto dto, String email) {
        Todo todo = findTodoById(id, email);

        if (todo != null) {
            todoMapper.updateTodoFromDto(dto, todo);
            repository.save(todo);

            return true;
        }

        return false;
    }

    public boolean delete(Long id, String email) {
        Todo todo = findTodoById(id, email);
        if (todo != null) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    private Todo findTodoById(Long id) {
        Optional<Todo> todoOpt = repository.findById(id);

        return todoOpt.orElse(null);
    }
    private Todo findTodoById(Long id, String email) {
        return repository.findByIdAndUserEmail(id, email);
    }
}
