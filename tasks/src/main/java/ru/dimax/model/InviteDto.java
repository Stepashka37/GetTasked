package ru.dimax.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InviteDto {

    private String comment;

    private UserDto inviter;

    private TaskDto taskDto;

}
