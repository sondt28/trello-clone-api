package com.son.todolist.section;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        SectionDto createdSection = sectionService.save(dto);
        URI uri = uriComponentsBuilder.path("/sections/{id}").buildAndExpand(createdSection.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<SectionDto> getSection(@PathVariable Long id) {
        SectionDto sectionDto = sectionService.get(id);
        return ResponseEntity.ok(sectionDto);
    }

    @PutMapping
    public ResponseEntity<Void> movingSection(@RequestBody SectionDto dto) {
        sectionService.moving(dto);

        return ResponseEntity.noContent().build();
    }


}
