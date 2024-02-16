package com.son.todolist.section;

import com.son.todolist.common.exception.NotFoundException;
import com.son.todolist.project.Project;
import com.son.todolist.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final ProjectRepository projectRepository;
    private final SectionMapper mapper;

    @Override
    public SectionDto save(SectionDto dto) {
        Project project = projectRepository
                .findById(dto.getProjectId())
                .orElseThrow(() -> new NotFoundException("Project not found."));

        Section section = mapper.dtoToSection(dto, project);
        sectionRepository.save(section);

        return mapper.sectionToDto(section);
    }

    @Override
    public SectionDto get(Long id) {
        Section section = sectionRepository.findById(id).orElse(null);

        if (section == null)
            throw new NotFoundException("Section not found");

        return mapper.sectionToDto(section);
    }

    @Override
    public void moving(SectionDto dto) {
        Section section = sectionRepository.findByIdAndProjectId(dto.getId(), dto.getProjectId());

        if (section == null)
            throw new NotFoundException("Section not found.");

        int totalSection = sectionRepository.countSectionByProjectId(dto.getProjectId());
        int oldOrder = section.getOrder();
        int newOrder = dto.getOrder();

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
