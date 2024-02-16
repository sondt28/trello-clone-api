package com.son.todolist.user;

import com.son.todolist.common.based.BaseEntity;
import com.son.todolist.project.Project;
import com.son.todolist.project.ProjectUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "`user`")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;
    private String fullName;
    private String password;
    private boolean isEnabled = false;

    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.ROLE_OWNER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Project> projects;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<ProjectUser> projectUsers;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private VerificationOTP verificationOtp;
}
