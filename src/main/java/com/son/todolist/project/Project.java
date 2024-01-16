package com.son.todolist.project;

import com.son.todolist.section.Section;
import com.son.todolist.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String hexColor;
    @Column(name = "`order`")
    private int order;
    private boolean isPublic = false;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Section> sections;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
