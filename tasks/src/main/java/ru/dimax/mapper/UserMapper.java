package ru.dimax.mapper;

import lombok.experimental.UtilityClass;
import ru.dimax.model.NewUserRequest;
import ru.dimax.model.User;
import ru.dimax.model.UserDto;

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
}
