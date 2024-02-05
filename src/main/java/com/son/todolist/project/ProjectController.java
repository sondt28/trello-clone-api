package com.son.todolist.project;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService service;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects() {
        List<ProjectDto> dtos = service.getAll();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectAndSectionDto> getProject(@PathVariable Long id) {
        ProjectAndSectionDto dto = service.get(id);

        if (dto == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProject(@PathVariable Long id,
                                              @RequestBody ProjectDto dto,
                                              Principal principal) {
        service.update(id, principal.getName(), dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> createProject(@Valid @RequestBody ProjectDto dto,
                                              UriComponentsBuilder uriComponentsBuilder,
                                              Principal principal) {
        ProjectDto savedProject = service.save(dto, principal.getName());
        URI uri = uriComponentsBuilder.path("/projects/{id}").buildAndExpand(savedProject.id()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id, Principal principal) {
        service.delete(id, principal.getName());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/users/{id-user}")
    public ResponseEntity<Void> addUserToProject(@PathVariable("id") Long id,
                                                 @PathVariable("id-user") Long userId) {
        service.addUserToProject(id, userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}/users/{id-user}")
    public ResponseEntity<Void> removeUserFromProject(@PathVariable("id") Long id,
                                                      @PathVariable("id-user") Long userId) {
        service.removeUserFromProject(id, userId);

        return ResponseEntity.noContent().build();
    }
}
