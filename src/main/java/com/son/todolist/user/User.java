package com.son.todolist.user;

import com.son.todolist.common.based.BaseEntity;
import com.son.todolist.project.Project;
import com.son.todolist.project.ProjectUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;
    private String fullName;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.ROLE_OWNER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Project> projects;

    @OneToMany(mappedBy = "user")
    private Set<ProjectUser> projectUsers;

    public User(String fullName, String email, RoleEnum role) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }
}
