package ru.dimax.model;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class NewTaskRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @FutureOrPresent
    private LocalDateTime start;

    @FutureOrPresent
    private LocalDateTime deadline;


}
