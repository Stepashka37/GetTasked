package ru.dimax.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewUserRequest {

    private String name;
    private String password;
    private String email;
    private Spec spec;
    private Grade grade;

}
