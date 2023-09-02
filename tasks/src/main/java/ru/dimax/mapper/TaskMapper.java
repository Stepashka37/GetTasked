package ru.dimax.mapper;

import lombok.experimental.UtilityClass;
import ru.dimax.model.*;

import static ru.dimax.mapper.UserMapper.*;
import java.util.stream.Collectors;

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
                /*.executors(task.getUserTasks().stream()
                        .map(UserTask::getUser)
                        .map(UserMapper::modelToDto)
                        .collect(Collectors.toList()))*/
                .executors(task.getUsers().stream()
                        .map(UserMapper::modelToDto)
                        .collect(Collectors.toList()))
                .responsible(UserMapper.modelToDto(task.getResponsible()))
                .build();
    }

    public Task dtoToModel(NewTaskRequest request) {
        return Task.builder()
                .id(0)
                .name(request.getName())
                .description(request.getDescription())
                .status(Status.PENDING)
                .start(request.getStart())
                .deadline(request.getDeadline())
                .build();
    }

    public ShortTaskDto modelToShortDto(Task task) {
        return ShortTaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .start(task.getStart())
                .deadline(task.getDeadline())
                .build();
    }

}
