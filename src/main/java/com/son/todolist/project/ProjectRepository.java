package com.son.todolist.project;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.sections s WHERE p.id = :projectId AND user.email = :email ORDER BY s.order ASC")
    Optional<Project> findProjectWithSortedSections(@Param("projectId") Long projectId, @Param("email") String email);
}
