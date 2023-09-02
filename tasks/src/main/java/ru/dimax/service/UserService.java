package ru.dimax.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dimax.exceptions.ConditionViolationException;
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

        log.info("User with id {} saved", userSaved.getId());
        return UserMapper.modelToDto(userSaved);
    }

    public UserDto updateUserByAdmin(Integer userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        UserUpdateMapper.INSTANCE.updateUserFromDto(request, user);

        User userSaved = userRepository.save(user);
        log.info("User with id {} updated", user.getId());
        return UserMapper.modelToDto(userSaved);
    }

    public FullUserDto getUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        log.info("User with id {} retrieved", user.getId());
        return modelToFullDto(user);
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

    public TaskDto setUserResponsible(Integer userId, Integer taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s does not exist", userId)));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task %d not found", taskId)));

        if (task.getResponsible().getId().equals(userId)) {
            throw new ConditionViolationException("This user is already marked as responsible");
        }

        Integer userIdToDelete = task.getResponsible().getId();
        task.setResponsible(user);
        //task.getUsers().removeIf(u -> u.getId().equals(userIdToDelete));
        if(!task.getUsers().contains(user)) {
            task.getUsers().add(user);
        }
        Task saved = taskRepository.saveAndFlush(task);

        return modelToDto(saved);
    }

    public TaskDto assignUserToTask(Integer userId, Integer taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task %d not found", taskId)));
        if (task.getResponsible().getId().equals(userId) ||
        task.getUsers().contains(user)) {
            throw new ConditionViolationException("This user is already participating this task");
        }

        if(user.getTasks().size() > 3) {
            throw new ConditionViolationException("This user already has 3 tasks to complete. He is very busy.");
        }

        task.getUsers().add(user);
        Task saved = taskRepository.saveAndFlush(task);

        return modelToDto(saved);
    }






}
