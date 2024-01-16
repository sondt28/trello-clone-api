package com.son.todolist.project;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService service;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects() {
        String email = getNameFromAuthentication();
        List<ProjectDto> dtos = service.getAll(email);

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("{projectId}/order/{newOrder}")
    public ResponseEntity<Void> moving(@PathVariable("projectId") Long projectId, @PathVariable("newOrder") int newOrder) {
        String email = getNameFromAuthentication();

        service.moving(projectId, email, newOrder);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable Long id) {
        String email = getNameFromAuthentication();
        ProjectDto dto = service.get(id, email);

        if (dto == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProject(@PathVariable Long id, @RequestBody ProjectDto dto) {
        String email = getNameFromAuthentication();
        service.update(id, email, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> createProject(@Valid @RequestBody ProjectDto dto, UriComponentsBuilder uriComponentsBuilder) {
        String email = getNameFromAuthentication();
        ProjectDto savedProject = service.save(dto, email);
        URI uri = uriComponentsBuilder.path("/projects/{id}").buildAndExpand(savedProject.id()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        String email = getNameFromAuthentication();
        service.delete(id, email);

        return ResponseEntity.noContent().build();
    }

    private String getNameFromAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
