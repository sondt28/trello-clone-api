package com.son.todolist.section;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends CrudRepository<Section, Long> {
    Section findByIdAndProjectId(Long id, Long projectId);

    int countSectionByProjectId(Long projectId);

    @Modifying
    @Transactional
    @Query("UPDATE Section " +
            "SET order = order + 1 " +
            "WHERE order >= :newOrder AND order < :oldOrder AND project.id = :projectId")
    void incrementPrevToPosition(@Param("newOrder") int newOrder, @Param("oldOrder") int oldOrder, @Param("projectId") Long projectId);

    @Modifying
    @Transactional
    @Query("UPDATE Section " +
            "SET order = order - 1 " +
            "WHERE order > :oldOrder AND order <= :newOrder AND project.id = :projectId")
    void decrementNextToPosition(@Param("newOrder") int newOrder, @Param("oldOrder") int oldOrder, @Param("projectId") Long projectId);
}
