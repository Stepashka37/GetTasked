package ru.dimax.mapper;

import lombok.experimental.UtilityClass;
import ru.dimax.model.request.NewRequestDto;
import ru.dimax.model.request.Request;
import ru.dimax.model.request.RequestDto;
import ru.dimax.model.task.Task;
import ru.dimax.model.user.User;
import ru.dimax.model.user.UserDto;

import static ru.dimax.mapper.TaskMapper.*;
import static ru.dimax.mapper.UserMapper.*;


@UtilityClass
public class RequestMapper {

    public Request dtoToModel(NewRequestDto dto) {
        return Request.builder()
                .id(0)
                .description(dto.getDescription())
                .task(new Task())
                .requester(new User())
                .active(true)
                .build();
    }

    public RequestDto modelToDto(Request request) {
        return RequestDto.builder()
                .description(request.getDescription())
                .userDto(UserMapper.modelToDto(request.getRequester()))
                .taskDto(TaskMapper.modelToDto(request.getTask()))
                .active(request.getActive())
                .build();
    }
}
