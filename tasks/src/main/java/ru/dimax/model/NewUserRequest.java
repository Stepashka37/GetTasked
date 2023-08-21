package ru.dimax.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Data
public class NewUserRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{4,16}$")
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Spec spec;

    @NotNull
    private Grade grade;

}
