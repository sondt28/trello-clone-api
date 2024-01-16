package com.son.todolist.project;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByIdAndUserEmail(Long id, String email);

    List<Project> findByUserEmailOrderByOrderAsc(String email);

    int countProjectByUserEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Project " +
            "SET order = order + 1 " +
            "WHERE order >= :newOrder AND order < :oldOrder AND user.email = :userEmail")
    void incrementBelowToPosition(@Param("newOrder") int newOrder,
                                  @Param("oldOrder") int oldOrder,
                                  @Param("userEmail") String email);

    @Modifying
    @Transactional
    @Query("UPDATE Project " +
            "SET order = order - 1 " +
            "WHERE order <= :newOrder AND order > :oldOrder AND user.email = :userEmail")
    void decrementAboveToPosition(@Param("newOrder") int newOrder,
                                  @Param("oldOrder") int oldOrder,
                                  @Param("userEmail") String email);
}
