package ru.dimax.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaskDto {

    private Integer id;
    private String name;
    private String description;
    private Status status;
    private List<UserDto> executors;
    private LocalDateTime start;
    private LocalDateTime deadline;


}
