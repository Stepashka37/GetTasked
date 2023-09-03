package ru.dimax.mapper;

import org.junit.jupiter.api.Test;
import ru.dimax.model.task.Status;
import ru.dimax.model.task.Task;
import ru.dimax.model.task.TaskDto;
import ru.dimax.model.user.Grade;
import ru.dimax.model.user.Spec;
import ru.dimax.model.user.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.dimax.mapper.TaskMapper.*;

class TaskMapperTest {

    @Test
    void itShouldMapTaskToDto() {
        // Given

        User user1 = User.builder()
                .id(1)
                .name("Dmitry")
                .email("mail@mail.ru")
                .password("12345")
                .grade(Grade.JUNIOR)
                .spec(Spec.BACKEND)
                .build();

        User user2 = User.builder()
                .id(2)
                .name("Dmitry")
                .email("mail@mail.ru")
                .password("12345")
                .grade(Grade.SENIOR)
                .spec(Spec.BACKEND)
                .build();

        Task task1 = Task.builder()
                .id(1)
                .name("task")
                .description("description")
                .start(LocalDateTime.now())
                .deadline(LocalDateTime.now().plusDays(1))
                .responsible(user1)
                .status(Status.IN_PROGRESS)
               .build();




        // When
        TaskDto dto = modelToDto(task1);
        // Then
        assertThat(dto.getName()).isEqualTo("task");
        assertThat(dto.getDescription()).isEqualTo("description");
        assertThat(dto.getStart()).isEqualTo(task1.getStart());
        assertThat(dto.getDeadline()).isEqualTo(task1.getDeadline());
        assertThat(dto.getStatus()).isEqualTo(task1.getStatus());
        assertThat(dto.getExecutors().size()).isEqualTo(2);
        assertThat(dto.getResponsible().getId()).isEqualTo(user1.getId());
    }
}