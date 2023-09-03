package ru.dimax.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.dimax.model.user.UpdateUserRequest;
import ru.dimax.model.user.User;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserUpdateMapper {
    UserUpdateMapper INSTANCE = Mappers.getMapper(UserUpdateMapper.class);

    @Mapping(target = "id", ignore = true)
    //@Mapping(target = "name", ignore = true)
    //@Mapping(target = "email", ignore = true)
    //@Mapping(target = "grade", ignore = true)
    //@Mapping(target = "spec", ignore = true)
    void updateUserFromDto(UpdateUserRequest dto, @MappingTarget User user);
}
