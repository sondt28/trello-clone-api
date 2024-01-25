package com.son.todolist.project;

import com.son.todolist.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class ProjectUserId implements Serializable {
    @Column(name = "project_id")
    Long projectId;
    @Column(name = "user_id")
    Long userId;
}
