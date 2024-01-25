package com.son.todolist.section;

import com.son.todolist.common.exception.NotFoundException;
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

    public SectionDto save(SectionDto dto) {
        Project project = projectRepository.findById(dto.projectId()).orElse(null);

        if (project == null)
            return null;

        Section section = mapper.dtoToSection(dto, project);
        sectionRepository.save(section);

        return mapper.sectionToDto(section);
    }

    public SectionDto get(Long id) {
        Section section = sectionRepository.findById(id).orElse(null);

        if (section == null)
            throw new NotFoundException("Section not found");

        return mapper.sectionToDto(section);
    }

    public void moving(SectionDto dto) {
        Section section = sectionRepository.findByIdAndProjectId(dto.id(), dto.projectId());

        if (section == null)
            throw new NotFoundException("Section not found.");

        int totalSection = sectionRepository.countSectionByProjectId(dto.projectId());
        int oldOrder = section.getOrder();
        int newOrder = dto.order();

        if (newOrder < oldOrder && newOrder >= 0)
            sectionRepository.incrementPrevToPosition(newOrder, oldOrder, section.getProject().getId());
        else if (newOrder > oldOrder && newOrder < totalSection)
            sectionRepository.decrementNextToPosition(newOrder, oldOrder, section.getProject().getId());
        else
            throw new NotFoundException("Invalid order");

        section.setOrder(newOrder);
        sectionRepository.save(section);
    }
}
