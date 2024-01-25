package com.son.todolist.todo;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    private final TodoService service;

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> findTodo(@PathVariable Long id) {
        TodoDto dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("{section-id}/section")
    public ResponseEntity<List<TodoDto>> findTodoBySection(@PathVariable Long sectionId) {
        List<TodoDto> dtos = service.findAllBySection(sectionId);

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}/move-on-section")
    public ResponseEntity<Void> moveTodoOnSection(@PathVariable(name = "id") Long todoId,
                                                  @RequestParam Long sectionId,
                                                  @RequestParam int newOrder) {

        service.moveOnSection(sectionId, todoId, newOrder);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/move-to-section")
    public ResponseEntity<Void> moveToSection(@PathVariable(name = "id") Long todoId,
                                              @RequestParam Long sectionId,
                                              @RequestParam Long newSectionId,
                                              @RequestParam int order) {
        service.moveToSection(todoId, sectionId, newSectionId, order);

        return ResponseEntity.noContent().build();
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

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoDto dto
    ) {
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
