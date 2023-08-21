package ru.dimax.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.dimax.model.Task;
import ru.dimax.model.UpdateTaskRequest;
import ru.dimax.model.UpdateUserRequest;
import ru.dimax.model.User;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskUpdateMapper {
    TaskUpdateMapper INSTANCE = Mappers.getMapper(TaskUpdateMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "spec", ignore = true)
    @Mapping(target = "responsible", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateTaskFromDto(UpdateTaskRequest dto, @MappingTarget Task task);
}