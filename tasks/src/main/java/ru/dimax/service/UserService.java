package ru.dimax.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dimax.exceptions.TaskNotFoundException;
import ru.dimax.exceptions.UserNotFoundException;
import ru.dimax.mapper.UserMapper;
import ru.dimax.mapper.UserUpdateMapper;
import ru.dimax.model.*;
import ru.dimax.repository.TaskRepository;
import ru.dimax.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.dimax.mapper.UserMapper.*;
import static ru.dimax.mapper.TaskMapper.*;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserDto createUser(NewUserRequest userRequest) {
        User user = UserMapper.dtoToModel(userRequest);

        User userSaved = userRepository.saveAndFlush(user);

        log.info("User with id {} saved", user.getId());
        return UserMapper.modelToDto(userSaved);
    }

    public UserDto updateUserByAdmin(Integer userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        UserUpdateMapper.INSTANCE.updateUserFromDto(request, user);

        User userUPD = userRepository.save(user);
        log.info("User with id {} updated", user.getId());
        return UserMapper.modelToDto(userUPD);
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        userRepository.deleteById(userId);
        log.info("User with id {} deleted", userId);
    }

    public List<UserDto> getAllUsersExecutingTask(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task %d not found", taskId)));

        List<User> users = userRepository.getUsersExecutingTask(taskId);
        log.info("Requested all users executing task with id {}", taskId);

        return users.stream()
                .map(u -> modelToDto(u))
                .collect(Collectors.toList());
    }



}
