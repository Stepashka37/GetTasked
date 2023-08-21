package ru.dimax.mapper;

import lombok.experimental.UtilityClass;
import ru.dimax.model.Task;
import ru.dimax.model.TaskDto;

@UtilityClass
public class TaskMapper {

    public TaskDto modelToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .start(task.getStart())
                .deadline(task.getDeadline())
                .executors(task.getUserTasks().)
                .build();
    }

}
