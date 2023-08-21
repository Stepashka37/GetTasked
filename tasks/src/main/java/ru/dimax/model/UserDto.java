package ru.dimax.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private Spec spec;
    private Grade grade;
}
