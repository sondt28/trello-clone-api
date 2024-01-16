package com.son.todolist.section;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<Void> createSection(@Valid @RequestBody SectionDto dto, UriComponentsBuilder uriComponentsBuilder) {
        String email = getNameFromAuthentication();
        SectionDto createdSection = sectionService.save(dto, email);
        URI uri = uriComponentsBuilder.path("/sections/{id}").buildAndExpand(createdSection.id()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<SectionDto> getSection(@PathVariable Long id) {
        String email = getNameFromAuthentication();

        return ResponseEntity.ok().build();
    }

    private String getNameFromAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
