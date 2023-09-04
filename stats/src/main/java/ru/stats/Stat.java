package ru.stats;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer userId;

    @Column
    private Integer taskId;

    @Column
    private String taskDescription;

    @Column
    private LocalDateTime finishTime;

}
