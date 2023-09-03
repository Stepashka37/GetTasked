package ru.dimax.model.task;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShortTaskDto {
    private Integer id;
    private String name;
    private String description;
    private Status status;
    private LocalDateTime start;
    private LocalDateTime deadline;

}
