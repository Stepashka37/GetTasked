package ru.dimax.model.request;

import lombok.Builder;
import lombok.Data;
import ru.dimax.model.task.ShortTaskDto;
import ru.dimax.model.task.Task;
import ru.dimax.model.task.TaskDto;
import ru.dimax.model.user.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class RequestDto {

    private String description;

    private UserDto userDto;

    private TaskDto taskDto;

    private Boolean active;

}
