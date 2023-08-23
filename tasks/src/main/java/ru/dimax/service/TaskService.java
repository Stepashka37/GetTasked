package ru.dimax.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dimax.exceptions.ConditionViolationException;
import ru.dimax.exceptions.TaskNotFoundException;
import ru.dimax.exceptions.UserNotFoundException;
import ru.dimax.mapper.TaskUpdateMapper;
import ru.dimax.mapper.UserUpdateMapper;
import ru.dimax.model.*;
import ru.dimax.repository.TaskRepository;
import ru.dimax.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static ru.dimax.mapper.TaskMapper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public TaskDto createTask(Integer userId, NewTaskRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        Task task = dtoToModel(request);
        task.setResponsible(user);
        task.setUsers(List.of(user));

        Task taskSaved = taskRepository.save(task);

        return modelToDto(task);
    }

    public TaskDto updateTaskByAdmin(Integer taskId, UpdateTaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id '%s' does not exist", taskId)));

        if (request.getStart().isBefore(task.getStart().plusDays(1)) ||
        request.getDeadline().isBefore(task.getDeadline().plusDays(1))) {
            throw new ConditionViolationException("The start and deadline time cannot be earlier that they were before");
        }

        TaskUpdateMapper.INSTANCE.updateTaskFromDto(request, task);

        Task taskUPD = taskRepository.save(task);

        return modelToDto(task);
    }


    public List<TaskDto> getAllCurrentUserTasks(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        List<Task> tasks = taskRepository.findTasksByUserId(userId);
        log.info("Requested all current task for user with id {}", userId);

        return tasks.stream()
                .map(t -> modelToDto(t))
                .collect(Collectors.toList());
    }


    //При пометке задачи как DONE, у всех юзеров она должна пропадать из списка выполняемых задач
    @Transactional
    public TaskDto reportUponTaskCompletion(Integer userId, Integer taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task %d not found", taskId)));

        if (task.getResponsible().getId() != userId) {
            throw new ConditionViolationException(String.format("User with id '%s' is not responsible for task %d", userId, taskId));
        }

        task.setStatus(Status.DONE);
        Task saved = taskRepository.saveAndFlush(task);

        List<User> usersExecutingThisTask = userRepository.getUsersExecutingTask(taskId);

        for (User user1 : usersExecutingThisTask) {
            user.getTasks().removeIf(t -> t.getId().equals(taskId));
        }

        userRepository.saveAll(usersExecutingThisTask);

        return modelToDto(task);
    }




}
