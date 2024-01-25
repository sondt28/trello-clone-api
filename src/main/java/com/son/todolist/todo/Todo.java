package com.son.todolist.todo;

import com.son.todolist.section.Section;
import com.son.todolist.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private int priority = 1;
    private LocalDateTime createAt = LocalDateTime.now();
    private LocalDateTime dueDate;
    @Column(name = "`order`")
    private int order;
    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
}
