package ru.dimax.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class TaskDto {

    private Integer id;
    private String name;
    private String description;
    private Status status;
    private UserDto responsible;
    private List<UserDto> executors;
    private LocalDateTime start;
    private LocalDateTime deadline;


}
