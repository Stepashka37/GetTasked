package ru.dimax.model.invite;

import lombok.Builder;
import lombok.Data;
import ru.dimax.model.task.TaskDto;
import ru.dimax.model.user.UserDto;

@Data
@Builder
public class InviteDto {

    private String comment;

    private UserDto inviter;

    private TaskDto taskDto;

}
