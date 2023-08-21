package ru.dimax.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @OneToMany(mappedBy = "task")
    private List<UserTask> userTasks;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(start, task.start) && Objects.equals(deadline, task.deadline) && Objects.equals(userTasks, task.userTasks) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, start, deadline, userTasks, status);
    }
}

