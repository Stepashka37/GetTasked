package ru.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatDto {

    private Integer userId;

    private Integer taskId;

    private String taskDescription;

    private LocalDateTime finishTime;

}
