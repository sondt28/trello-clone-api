package com.son.todolist.section;

import com.son.todolist.common.based.BaseEntity;
import com.son.todolist.project.Project;
import com.son.todolist.todo.Todo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Section extends BaseEntity {
    private String name;
    @Column(name = "`order`")
    private int order;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private Set<Todo> todos;
}
