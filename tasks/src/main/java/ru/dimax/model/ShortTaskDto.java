package ru.dimax.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
