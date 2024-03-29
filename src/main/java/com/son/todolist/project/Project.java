package com.son.todolist.project;

import com.son.todolist.common.based.BaseEntity;
import com.son.todolist.section.Section;
import com.son.todolist.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Project extends BaseEntity {
    private String name;
    private String hexColor;
    private boolean isPublic;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Section> sections;
    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User user;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<ProjectUser> users;
}
