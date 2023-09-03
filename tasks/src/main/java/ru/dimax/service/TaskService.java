package ru.dimax.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dimax.exceptions.ConditionViolationException;
import ru.dimax.exceptions.TaskNotFoundException;
import ru.dimax.exceptions.UserNotFoundException;
import ru.dimax.mapper.TaskUpdateMapper;
import ru.dimax.model.task.*;
import ru.dimax.model.user.User;
import ru.dimax.repository.TaskRepository;
import ru.dimax.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

        if (request.getDeadline().isBefore(request.getStart())) {
            throw new ConditionViolationException("Task deadline cannot be before task start");
        }

        Task task = dtoToModel(request);
        task.setResponsible(user);
        task.setUsers(List.of(user));

        Task taskSaved = taskRepository.save(task);
        log.info("Task with id {} created", taskSaved.getId());
        return modelToDto(taskSaved);
    }

    public TaskDto getTask(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id '%s' does not exist", taskId)));

        log.info("Get info for task {}", task.getId());
        return modelToDto(task);
    }

    public TaskDto updateTaskByAdmin(Integer taskId, UpdateTaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id '%s' does not exist", taskId)));

        if ((request.getStart() != null && request.getDeadline() == null) ||
                (request.getStart() == null && request.getDeadline() != null)) {
            throw new ConditionViolationException("The start and deadline can be changed only together");
        }

        if (request.getStart() != null && request.getDeadline() != null) {
            if (request.getStart().isBefore(task.getStart().plusDays(1)) ||
                    request.getDeadline().isBefore(task.getDeadline().plusDays(1))) {
                throw new ConditionViolationException("The start and deadline time cannot be earlier that they were before");
            }
        }

        TaskUpdateMapper.INSTANCE.updateTaskFromDto(request, task);

        Task taskUPD = taskRepository.save(task);
        log.info("Task with id {} created", task.getId());

        return modelToDto(taskUPD);
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
            throw new ConditionViolationException(String.format("User with id %s is not responsible for task %d", userId, taskId));
        }

        task.setStatus(Status.DONE);
        Task saved = taskRepository.saveAndFlush(task);

        List<User> usersExecutingThisTask = userRepository.getUsersExecutingTask(taskId);

        for (User user1 : usersExecutingThisTask) {
            user1.getTasks().removeIf(t -> t.getId().equals(taskId));
        }

        task.setUsers(new ArrayList<>());

        userRepository.saveAll(usersExecutingThisTask);
        log.info("Task with id {} completed", task.getId());

        return modelToDto(saved);
    }

    public TaskDto approveTask(Boolean approve, Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task %d not found", taskId)));

        if (task.getStatus().equals(Status.IN_PROGRESS) || task.getStatus().equals(Status.DONE)) {
            throw new ConditionViolationException(
                    String.format("Task %s cannot be approved or rejected since it is already IN_PROGRESS/DONE", task.getId())
            );
        }
        if (approve) {
            task.setStatus(Status.IN_PROGRESS);
        } else {
            task.setStatus(Status.CANCELLED);
            List<User> usersExecutingThisTask = userRepository.getUsersExecutingTask(taskId);

            for (User user1 : usersExecutingThisTask) {
                user1.getTasks().removeIf(t -> t.getId().equals(taskId));
            }

            task.setUsers(new ArrayList<>());

            userRepository.saveAll(usersExecutingThisTask);
        }

        Task saved  = taskRepository.saveAndFlush(task);
        log.info("Task with id {} was set to {}", task.getId(), task.getStatus().toString());

        return modelToDto(saved);
    }




}
