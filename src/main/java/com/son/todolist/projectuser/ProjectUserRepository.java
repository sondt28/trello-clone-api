package com.son.todolist.projectuser;

import com.son.todolist.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, ProjectUserId> {
    @Query("SELECT pu.project " +
            "FROM ProjectUser pu " +
            "WHERE pu.user.email = :#{authentication.name}")
    List<Project> findProjectsByUserEmail();

    @Query("SELECT pu.project " +
            "FROM ProjectUser pu " +
            "JOIN Section s " +
            "WHERE pu.id.projectId = :projectId AND pu.user.email = :#{authentication.name} " +
            "ORDER BY s.order ASC")
    Optional<Project> findProjectByUserEmail(@Param("projectId") Long projectId);

}
