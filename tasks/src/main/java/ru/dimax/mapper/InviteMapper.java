package ru.dimax.mapper;

import lombok.experimental.UtilityClass;
import ru.dimax.model.Invite;
import ru.dimax.model.InviteDto;
import ru.dimax.model.NewInviteDto;
import ru.dimax.mapper.UserMapper.*;
import ru.dimax.mapper.TaskMapper.*;


@UtilityClass
public class InviteMapper {

    public Invite dtoToModel(NewInviteDto dto) {
        return Invite.builder()
                .id(0)
                .comment(dto.getComment())
                .build();
    }

    public InviteDto modelToDto(Invite invite) {
        return InviteDto.builder()
                .comment(invite.getComment())
                .inviter(UserMapper.modelToDto(invite.getInviter()))
                .taskDto(TaskMapper.modelToDto(invite.getTask()))
                .build();
    }


}
