package com.son.todolist.project;

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
}
