package ru.dimax.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.dimax.model.task.Status;
import ru.dimax.model.task.Task;
import ru.dimax.model.user.Grade;
import ru.dimax.model.user.Spec;
import ru.dimax.model.user.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles(profiles = {"test"})
@Transactional
class TaskRepositoryTest {

    @Autowired
    private TaskRepository underTest;

    @Autowired
    private UserRepository userRepository;

    @Test
    void itShouldBeInitialized() {
        // Given
        // When
        // Then
        assertThat(underTest).isNotNull();
    }

    @Test
    void itShouldFindTasksByUserId() {
        // Given

        User user = User.builder()
                .id(1)
                .name("Dmitry")
                .email("mail@mail.ru")
                .password("12345")
                .grade(Grade.JUNIOR)
                .spec(Spec.BACKEND)
                .build();

        Task task1 = Task.builder()
                .id(1)
                .name("task")
                .description("description")
                .start(LocalDateTime.now())
                .deadline(LocalDateTime.now().plusDays(1))
                .status(Status.IN_PROGRESS)
                .users(List.of(user))
                .build();

        Task task2 = Task.builder()
                .id(2)
                .name("task")
                .description("description")
                .start(LocalDateTime.now())
                .deadline(LocalDateTime.now().plusDays(2))
                .status(Status.IN_PROGRESS)
                .users(List.of(user))
                .build();

        // When
        userRepository.saveAndFlush(user);
        underTest.saveAndFlush(task1);
        underTest.saveAndFlush(task2);
        // Then
        List<Task> all = underTest.findAll();
        List<Task> tasks = underTest.findTasksByUserId(user.getId());

        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks.get(0).getId()).isEqualTo(task1.getId());
        assertThat(tasks.get(1).getId()).isEqualTo(task2.getId());

    }
}