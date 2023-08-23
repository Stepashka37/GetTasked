package ru.dimax.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.dimax.model.Task;
import ru.dimax.model.UpdateTaskRequest;
import ru.dimax.model.UpdateUserRequest;
import ru.dimax.model.User;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskUpdateMapper {
    TaskUpdateMapper INSTANCE = Mappers.getMapper(TaskUpdateMapper.class);

    @Mapping(target = "id", ignore = true)
    //@Mapping(target = "name", ignore = true)
   //@Mapping(target = "description", ignore = true)
   //@Mapping(target = "responsible", ignore = true)
  //@Mapping(target = "users", ignore = true)
    void updateTaskFromDto(UpdateTaskRequest dto, @MappingTarget Task task);
}