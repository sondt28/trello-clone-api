package com.son.todolist.todo;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findBySectionId(Long id);

    Optional<Todo> findByIdAndSectionId(Long id, Long sectionId);

    @Query("SELECT t FROM Todo t WHERE t.id = :id AND section.project.user.email = :#{authentication.name}")
    Optional<Todo> findByTodoId(Long id);

    int countTodoBySectionId(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Todo " +
            "SET order = order + 1 " +
            "WHERE order >= :newOrder AND order < :oldOrder AND section.id = :sectionId")
    void incrementBelowToPosition(@Param("newOrder") int newOrder,
                                  @Param("oldOrder") int oldOrder,
                                  @Param("sectionId") Long sectionId);

    @Modifying
    @Transactional
    @Query("UPDATE Todo " +
            "SET order = order - 1 " +
            "WHERE order <= :newOrder AND order > :oldOrder AND section.id = :sectionId")
    void decrementAboveToPosition(@Param("newOrder") int newOrder,
                                  @Param("oldOrder") int oldOrder,
                                  @Param("sectionId") Long sectionId);

    @Modifying
    @Transactional
    @Query("UPDATE Todo " +
            "SET order = order + 1 " +
            "WHERE order >= :newOrder AND section.id = :sectionId")
    void incrementOrder(@Param("newOrder") int newOrder, @Param("sectionId") Long sectionId);

    @Modifying
    @Transactional
    @Query("UPDATE Todo " +
            "SET order = order - 1 " +
            "WHERE order > :order AND section.id = :sectionId")
    void decrementOrder(@Param("order") int order, @Param("sectionId") Long sectionId);
}
