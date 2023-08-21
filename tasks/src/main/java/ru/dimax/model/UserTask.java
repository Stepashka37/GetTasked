package ru.dimax.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTask {

    @EmbeddedId
    private UserTaskId id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("taskId")
    private Task task;

    @Column(name = "responsible")
    private boolean responsible;

    // Getters and setters
}
