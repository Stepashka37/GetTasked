package ru.dimax.model.request;

import lombok.*;
import ru.dimax.model.task.Task;
import ru.dimax.model.user.User;

import javax.persistence.*;

@Entity
@Table(name = "requests")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", length = 500)
    private String description;

    @OneToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column
    private Boolean active;
}
