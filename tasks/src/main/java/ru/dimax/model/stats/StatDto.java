package ru.dimax.model.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatDto {

    private Integer userId;

    private Integer taskId;

    private String taskDescription;

    private LocalDateTime finishTime;

}
