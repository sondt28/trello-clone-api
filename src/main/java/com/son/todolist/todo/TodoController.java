package com.son.todolist.todo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> findTodoByOwner(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TodoDto dto = service.findByOwner(id, authentication.getName());
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> findAllTodoByOwner(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Page<TodoDto> page = service.findByUser(pageable, authentication.getName());

        return ResponseEntity.ok(page.getContent());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Todo>> findAllTodo(Pageable pageable) {
        Page<Todo> page = service.findAll(pageable);

        return ResponseEntity.ok(page.getContent());
    }

    @PostMapping
    public ResponseEntity<Object> saveTodo(
            @Valid @RequestBody TodoDto dto,
            UriComponentsBuilder uriComponentsBuilder) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TodoDto savedTodo = service.save(dto, authentication.getName());
        URI locationOfNewTodo = uriComponentsBuilder.path("/todos/{id}").buildAndExpand(savedTodo.id()).toUri();

        return ResponseEntity.created(locationOfNewTodo).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoDto dto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUpdated = service.update(id, dto, authentication.getName());
        if (isUpdated)
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isDeleted = service.delete(id, authentication.getName());
        if (isDeleted)
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
