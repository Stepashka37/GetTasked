package ru.dimax.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dimax.exceptions.UserNotFoundException;
import ru.dimax.mapper.UserMapper;
import ru.dimax.mapper.UserUpdateMapper;
import ru.dimax.model.*;
import ru.dimax.repository.TaskRepository;
import ru.dimax.repository.UserRepository;

import java.util.List;

import static ru.dimax.mapper.UserMapper.*;

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
        log.info("User with id '%s' updated", user.getId());
        return UserMapper.modelToDto(userUPD);
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        userRepository.deleteById(userId);
    }

    public List<UserDto> getAllCurrentUserTasks(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        List<Task> tasks = taskRepository.findTasksByUserId(userId);

    }

}
