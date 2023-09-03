package ru.dimax.mapper;

import lombok.experimental.UtilityClass;
import ru.dimax.model.invite.Invite;
import ru.dimax.model.invite.InviteDto;
import ru.dimax.model.invite.NewInviteDto;


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
