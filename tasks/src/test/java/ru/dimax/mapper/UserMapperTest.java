package ru.dimax.mapper;

import org.junit.jupiter.api.Test;
import ru.dimax.model.user.*;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void itShouldMapDtoToUser() {
        // Given
        NewUserRequest request = NewUserRequest.builder()
                .email("mail@mail.ru")
                .password("12345")
                .name("Dmitry")
                .grade(Grade.JUNIOR)
                .spec(Spec.BACKEND)
                .build();

        User userMapped = UserMapper.dtoToModel(request);
        // When
        // Then

        assertThat(userMapped.getId()).isEqualTo(0);
        assertThat(userMapped.getName()).isEqualTo("Dmitry");
        assertThat(userMapped.getEmail()).isEqualTo("mail@mail.ru");
        assertThat(userMapped.getPassword()).isEqualTo("12345");
        assertThat(userMapped.getGrade()).isEqualTo(Grade.JUNIOR);
        assertThat(userMapped.getSpec()).isEqualTo(Spec.BACKEND);
    }

    @Test
    void itShouldMapUserToDto() {
        // Given
        User user = User.builder()
                .id(1)
                .name("Dmitry")
                .email("mail@mail.ru")
                .password("12345")
                .grade(Grade.JUNIOR)
                .spec(Spec.BACKEND)
                .build();

        UserDto userDto = UserMapper.modelToDto(user);

        assertThat(userDto.getId()).isEqualTo(1);
        assertThat(userDto.getName()).isEqualTo("Dmitry");
        assertThat(userDto.getEmail()).isEqualTo("mail@mail.ru");
        assertThat(userDto.getGrade()).isEqualTo(Grade.JUNIOR);
        assertThat(userDto.getSpec()).isEqualTo(Spec.BACKEND);

        // When
        // Then

    }
}