package ru.dimax.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.dimax.model.task.Task;
import ru.dimax.model.task.UpdateTaskRequest;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskUpdateMapper {
    TaskUpdateMapper INSTANCE = Mappers.getMapper(TaskUpdateMapper.class);

    @Mapping(target = "id", ignore = true)
    void updateTaskFromDto(UpdateTaskRequest dto, @MappingTarget Task task);
}