package com.son.todolist.section;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends CrudRepository<Section, Long> {

    int countSectionByProjectId(Long projectId);
}
