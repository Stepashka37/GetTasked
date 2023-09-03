package ru.dimax.model.user;

import lombok.Builder;
import lombok.Data;
import ru.dimax.model.task.ShortTaskDto;

import java.util.List;

@Data
@Builder
public class FullUserDto {

    private Integer id;

    private String name;

    private String email;

    private Spec spec;

    private Grade grade;

    private List<ShortTaskDto> tasks;
}
