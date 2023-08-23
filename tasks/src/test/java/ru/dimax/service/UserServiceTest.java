package ru.dimax.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dimax.mapper.UserUpdateMapper;
import ru.dimax.model.*;
import ru.dimax.repository.TaskRepository;
import ru.dimax.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static ru.dimax.mapper.UserMapper.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  TaskRepository taskRepository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    UserUpdateMapper userUpdateMapper = Mockito.mock(UserUpdateMapper.class);

    @InjectMocks
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, taskRepository);
    }

    @Test
    void itShouldCreateUser() {
        // Given
        NewUserRequest request = NewUserRequest.builder()
                .name("Dima")
                .password("123456")
                .email("mail@mail.ru")
                .grade(Grade.JUNIOR)
                .spec(Spec.BACKEND)
                .build();

        UserDto user = UserDto.builder()
                        .id(1)
                        .name(request.getName())
                        .email(request.getEmail())
                        .grade(request.getGrade())
                        .spec(request.getSpec())
                        .build();

        when(userRepository.saveAndFlush(any())).thenReturn(dtoToModel(request));
        // When
        UserDto userDto = underTest.createUser(request);
        // Then
        assertThat(userDto).isEqualToIgnoringGivenFields(user, "id");
        verify(userRepository, times(1)).saveAndFlush(any());

    }

    @Test
    void itShouldUpdateUserByAdmin() {
        // Given
        UpdateUserRequest request = UpdateUserRequest.builder()
                .name("Dima")
                .email("email@mail.ru")
                .grade(Grade.MIDDLE)
                .spec(Spec.BACKEND)
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

        User userUpd = User.builder()
                .id(1)
                .name("Dima")
                .email("email@mail.ru")
                .tasks(new ArrayList<>())
                .password(" ")
                .spec(Spec.BACKEND)
                .grade(Grade.MIDDLE)
                .build();

        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(userUpd);
        underTest.updateUserByAdmin(1, request);

        User userArgumentCaptorValue = userArgumentCaptor.getValue();
        assertThat(userArgumentCaptorValue)
                .isEqualToIgnoringGivenFields(userUpd, "id");
        assertThat(userArgumentCaptorValue.getId()).isNotNull();
        // When
        // Then

    }

    @Test
    void itShouldDeleteUser() {
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

        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(user));

        // When
        // Then
        underTest.deleteUser(1);
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void itShouldGetAllUsersExecutingTask() {
        // Given
        User user = User.builder()
                .id(1)
                .name("Dmitry")
                .email("mail@mail.ru")
                .password("12345")
                .grade(Grade.JUNIOR)
                .spec(Spec.BACKEND)
                .build();

        User user2 = User.builder()
                .id(2)
                .name("Alex")
                .email("AlexMail@mail.ru")
                .password("12345")
                .grade(Grade.MIDDLE)
                .spec(Spec.FRONTEND)
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

        when(taskRepository.findById(anyInt())).thenReturn(Optional.ofNullable(task1));
        when(userRepository.getUsersExecutingTask(anyInt())).thenReturn(List.of(user, user2));

        List<UserDto> dtos = underTest.getAllUsersExecutingTask(1);
        // When
        // Then
        assertThat(dtos.get(0)).isEqualTo(modelToDto(user));
        assertThat(dtos.get(1)).isEqualTo(modelToDto(user2));

    }
}