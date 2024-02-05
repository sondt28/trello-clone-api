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
    public ResponseEntity<TodoDto> findTodo(@PathVariable Long todoId) {
        TodoDto dto = service.findById(todoId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("{section-id}/section")
    public ResponseEntity<List<TodoDto>> findTodoBySection(@PathVariable Long sectionId) {
        List<TodoDto> dtos = service.findAllBySection(sectionId);

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<Object> saveTodo(
            @Valid @RequestBody TodoDto dto,
            UriComponentsBuilder uriComponentsBuilder) {

        TodoDto savedTodo = service.save(dto);
        URI locationOfNewTodo = uriComponentsBuilder.path("/todos/{id}")
                .buildAndExpand(savedTodo.id()).toUri();

        return ResponseEntity.created(locationOfNewTodo).build();
    }

    @PutMapping("/{todo-id}/move-on-section/{section-id}")
    public ResponseEntity<Void> moveTodoOnSection(@PathVariable Long todoId,
                                                  @PathVariable Long sectionId,
                                                  @Valid @RequestBody TodoOrderDto dto) {

        service.moveOnSection(todoId, sectionId, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{todo-id}/move-to-section/{section-id}")
    public ResponseEntity<Void> moveToSection(@PathVariable Long todoId,
                                              @PathVariable Long sectionId,
                                              @Valid @RequestBody TodoOrderDto dto) {
        service.moveToSection(todoId, sectionId, dto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{todo-id}")
    public ResponseEntity<Object> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoDto dto
    ) {
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
