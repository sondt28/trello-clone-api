package com.son.todolist.section;

public interface SectionService {
    SectionDto save(SectionDto dto);
    SectionDto get(Long id);
    void moving(SectionDto dto);
}
