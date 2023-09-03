package ru.dimax.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dimax.model.task.ShortTaskDto;
import ru.dimax.model.user.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewRequestDto {

    @NotBlank
    @Size(max = 500)
    private String description;

    private Integer userId;

    private Integer taskId;
}
