package com.son.todolist.todo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoServiceImpl service;

    @GetMapping("/{todo-id}")
    public ResponseEntity<TodoDto> findTodo(@PathVariable("todo-id") Long todoId) {
        TodoDto dto = service.findById(todoId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("{section-id}/section")
    public ResponseEntity<List<TodoDto>> findTodoBySection(@PathVariable("section-id") Long sectionId) {
        List<TodoDto> dtos = service.findAllBySection(sectionId);

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<Object> saveTodo(
            @Valid @RequestBody TodoDto dto,
            UriComponentsBuilder uriComponentsBuilder) {

        TodoDto savedTodo = service.save(dto);
        URI locationOfNewTodo = uriComponentsBuilder.path("/todos/{id}")
                .buildAndExpand(savedTodo.getId()).toUri();

        return ResponseEntity.created(locationOfNewTodo).build();
    }

    @PutMapping("/{todo-id}/move-on-section/{section-id}")
    public ResponseEntity<Void> moveTodoOnSection(@PathVariable("todo-id") Long todoId,
                                                  @PathVariable("section-id") Long sectionId,
                                                  @Valid @RequestBody TodoOrderDto dto) {

        service.moveOnSection(todoId, sectionId, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{todo-id}/move-to-section/{section-id}")
    public ResponseEntity<Void> moveToSection(@PathVariable("todo-id") Long todoId,
                                              @PathVariable("section-id") Long sectionId,
                                              @Valid @RequestBody TodoSectionDto dto) {
        service.moveToSection(todoId, sectionId, dto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{todo-id}")
    public ResponseEntity<Object> updateTodo(
            @PathVariable("todo-id") Long id,
            @Valid @RequestBody TodoDto dto
    ) {
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("todo-id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
