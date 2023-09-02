package ru.dimax.mapper;

import lombok.experimental.UtilityClass;
import ru.dimax.model.FullUserDto;
import ru.dimax.model.NewUserRequest;
import ru.dimax.model.User;
import ru.dimax.model.UserDto;

import java.util.stream.Collectors;
import static ru.dimax.mapper.TaskMapper.*;

@UtilityClass
public class UserMapper {

    public User dtoToModel(NewUserRequest request) {
        return User.builder()
                .id(0)
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .grade(request.getGrade())
                .spec(request.getSpec())
                .build();
    }

    public UserDto modelToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .grade(user.getGrade())
                .spec(user.getSpec())
                .build();
    }

    public FullUserDto modelToFullDto(User user) {
        return FullUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .grade(user.getGrade())
                .spec(user.getSpec())
                .tasks(user.getTasks().stream()
                        .map(t -> modelToShortDto(t))
                        .collect(Collectors.toList()))
                .build();
    }
}
