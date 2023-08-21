package ru.dimax.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUserRequest {

    private Spec spec;
    private Grade grade;
    private String email;
    private String name;
}
