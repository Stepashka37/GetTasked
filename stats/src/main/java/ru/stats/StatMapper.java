package ru.stats;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StatMapper {

    public Stat dtoToModel(StatDto dto) {
        return Stat.builder()
                .id(0)
                .userId(dto.getUserId())
                .taskId(dto.getTaskId())
                .taskDescription(dto.getTaskDescription())
                .finishTime(dto.getFinishTime())
                .build();
    }

    public StatDto modelToDto(Stat stat) {
        return StatDto.builder()
                .userId(stat.getUserId())
                .taskId(stat.getTaskId())
                .taskDescription(stat.getTaskDescription())
                .finishTime(stat.getFinishTime())
                .build();
    }
}
