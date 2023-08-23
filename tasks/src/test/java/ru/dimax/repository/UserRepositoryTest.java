package ru.dimax.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.dimax.model.*;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles(profiles = {"test"})
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void itShouldBeInitialized() {
        // Given
        // When
        // Then
        assertThat(underTest).isNotNull();
    }

    @Test
    void itShouldGetUsersExecutingTask() {
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
                .email("sdsd@mail.ru")
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
                .users(List.of(user1, user2))
                .build();
        // When
        underTest.save(user1);
        underTest.save(user2);
        taskRepository.saveAndFlush(task1);

        // Then
        List<User> users = underTest.getUsersExecutingTask(task1.getId());
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getId()).isEqualTo(user1.getId());
        assertThat(users.get(1).getId()).isEqualTo(user2.getId());

    }
}