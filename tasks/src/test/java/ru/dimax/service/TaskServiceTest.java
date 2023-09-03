package ru.dimax.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dimax.exceptions.ConditionViolationException;
import ru.dimax.mapper.UserUpdateMapper;
import ru.dimax.model.task.*;
import ru.dimax.model.user.Grade;
import ru.dimax.model.user.Spec;
import ru.dimax.model.user.User;
import ru.dimax.repository.TaskRepository;
import ru.dimax.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static ru.dimax.mapper.TaskMapper.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Captor
    private ArgumentCaptor<Task> taskArgumentCaptor;

    UserUpdateMapper userUpdateMapper = Mockito.mock(UserUpdateMapper.class);

    @InjectMocks
    private TaskService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TaskService(userRepository, taskRepository);
    }

    @Test
    void itShouldCreateTask() {
        // Given
        User user = User.builder()
                .id(1)
                .name("Dmitry")
                .email("mail@mail.ru")
                .tasks(new ArrayList<>())
                .password(" ")
                .spec(Spec.BACKEND)
                .grade(Grade.JUNIOR)
                .build();

        NewTaskRequest request = NewTaskRequest.builder()
                .name("Task")
                .description("Description")
                .start(LocalDateTime.now())
                .deadline(LocalDateTime.now().plusDays(1))
                .build();

        Task task = Task.builder()
                .id(1)
                .name("Task")
                .description("Description")
                .start(request.getStart())
                .deadline(request.getDeadline())
                .users(List.of(user))
                .responsible(user)
                .status(Status.PENDING)
                .build();
        // When
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(user));
        when(taskRepository.save(any())).thenReturn(task);

        TaskDto taskSaved = underTest.createTask(1, request);
        // Then
        assertThat(taskSaved).isEqualToIgnoringGivenFields(modelToDto(task), "id");
        assertThat(taskSaved.getResponsible().getId()).isEqualTo(1);
        assertThat(taskSaved.getExecutors().get(0).getId()).isEqualTo(1);

    }

    @Test
    void itShouldUpdateTaskByAdmin() {
        // Given
        UpdateTaskRequest request = UpdateTaskRequest.builder()
                .start(LocalDateTime.now().plusDays(1))
                .deadline(LocalDateTime.now().plusDays(2))
                .build();

        User user = User.builder()
                .id(1)
                .name("Dmitry")
                .email("mail@mail.ru")
                .tasks(new ArrayList<>())
                .password(" ")
                .spec(Spec.BACKEND)
                .grade(Grade.JUNIOR)
                .build();

        Task task = Task.builder()
                .id(1)
                .name("Task")
                .description("Description")
                .start(LocalDateTime.now())
                .deadline(LocalDateTime.now().plusDays(1))
                .users(List.of(user))
                .responsible(user)
                .status(Status.PENDING)
                .build();

        Task taskUPD = Task.builder()
                .id(1)
                .name("Task")
                .description("Description")
                .start(request.getStart())
                .deadline(request.getDeadline())
                .users(List.of(user))
                .responsible(user)
                .status(Status.PENDING)
                .build();

        when(taskRepository.findById(anyInt())).thenReturn(Optional.ofNullable(task));

        when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(taskUPD);
        underTest.updateTaskByAdmin(1, request);

        Task taskArgumentCaptorValue = taskArgumentCaptor.getValue();
        assertThat(taskArgumentCaptorValue)
                .isEqualToIgnoringGivenFields(taskUPD, "id");
        // When
        // Then

    }

    @Test
    void itShouldThrowWhenRequestHasStartAndDeadlineEarlierThanBefore() {
        // Given
        UpdateTaskRequest request = UpdateTaskRequest.builder()
                .start(LocalDateTime.now())
                .deadline(LocalDateTime.now().plusDays(1))
                .build();

        User user = User.builder()
                .id(1)
                .name("Dmitry")
                .email("mail@mail.ru")
                .tasks(new ArrayList<>())
                .password(" ")
                .spec(Spec.BACKEND)
                .grade(Grade.JUNIOR)
                .build();

        Task task = Task.builder()
                .id(1)
                .name("Task")
                .description("Description")
                .start(LocalDateTime.now().plusDays(1))
                .deadline(LocalDateTime.now().plusDays(2))
                .users(List.of(user))
                .responsible(user)
                .status(Status.PENDING)
                .build();

        Task taskUPD = Task.builder()
                .id(1)
                .name("Task")
                .description("Description")
                .start(request.getStart())
                .deadline(request.getDeadline())
                .users(List.of(user))
                .responsible(user)
                .status(Status.PENDING)
                .build();

        when(taskRepository.findById(anyInt())).thenReturn(Optional.ofNullable(task));

        assertThatThrownBy(() -> underTest.updateTaskByAdmin(1, request))
                .isInstanceOf(ConditionViolationException.class)
                .hasMessageContaining("The start and deadline time cannot be earlier that they were before");
        // When
        // Then

    }

    @Test
    void itShouldGetAllCurrentUserTasks() {
        // Given
        User user = User.builder()
                .id(1)
                .name("Dmitry")
                .email("mail@mail.ru")
                .tasks(new ArrayList<>())
                .password(" ")
                .spec(Spec.BACKEND)
                .grade(Grade.JUNIOR)
                .build();

        Task task = Task.builder()
                .id(1)
                .name("Task")
                .description("Description")
                .start(LocalDateTime.now())
                .deadline(LocalDateTime.now().plusDays(1))
                .users(List.of(user))
                .responsible(user)
                .status(Status.PENDING)
                .build();

        Task task2 = Task.builder()
                .id(2)
                .name("Task")
                .description("Description")
                .start(LocalDateTime.now())
                .deadline(LocalDateTime.now().plusDays(1))
                .users(List.of(user))
                .responsible(user)
                .status(Status.PENDING)
                .build();

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(taskRepository.findTasksByUserId(1)).thenReturn(List.of(task, task2));
        // When
        List<TaskDto> dtos = underTest.getAllCurrentUserTasks(1);
        // Then

        assertThat(dtos.size()).isEqualTo(2);
        assertThat(dtos.get(0)).isEqualTo(modelToDto(task));
        assertThat(dtos.get(1)).isEqualTo(modelToDto(task2));


    }
}