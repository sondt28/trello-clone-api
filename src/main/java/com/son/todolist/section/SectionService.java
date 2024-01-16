package com.son.todolist.section;

import com.son.todolist.project.Project;
import com.son.todolist.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SectionService {
    private final SectionRepository sectionRepository;
    private final ProjectRepository projectRepository;
    private final SectionMapper mapper;

    public SectionDto save(SectionDto dto, String email) {
        Project project = projectRepository.findByIdAndUserEmail(dto.projectId(), email);

        if (project == null)
            return null;

        Section section = mapper.dtoToSection(dto, project, email);
        sectionRepository.save(section);

        return mapper.sectionToDto(section);
    }
}
