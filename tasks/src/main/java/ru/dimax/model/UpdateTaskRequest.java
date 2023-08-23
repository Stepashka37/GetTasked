package ru.dimax.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UpdateTaskRequest {

    private LocalDateTime start;
    private LocalDateTime deadline;

}
