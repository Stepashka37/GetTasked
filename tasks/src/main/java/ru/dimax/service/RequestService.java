package ru.dimax.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dimax.exceptions.ConditionViolationException;
import ru.dimax.exceptions.RequestNotFoundException;
import ru.dimax.exceptions.TaskNotFoundException;
import ru.dimax.exceptions.UserNotFoundException;
import ru.dimax.model.request.NewRequestDto;
import ru.dimax.model.request.Request;
import ru.dimax.model.request.RequestDto;
import ru.dimax.model.task.Status;
import ru.dimax.model.task.Task;
import ru.dimax.model.task.TaskDto;
import ru.dimax.model.user.User;
import ru.dimax.repository.RequestRepository;
import ru.dimax.repository.TaskRepository;
import ru.dimax.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.dimax.mapper.RequestMapper.*;
import static ru.dimax.mapper.TaskMapper.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final RequestRepository requestRepository;

    public RequestDto createRequest(NewRequestDto dto) {
        Request request = dtoToModel(dto);

        Integer userId = dto.getUserId();
        Integer taskId = dto.getTaskId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s does not exist", userId)));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id %s does not exist", taskId)));

        request.setRequester(user);
        request.setTask(task);

        Request requestSaved = requestRepository.saveAndFlush(request);

        log.info("Request {} has been created", requestSaved.getId());

        return modelToDto(requestSaved);
    }

    public TaskDto answerToRequest(Integer userId, Integer requestId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s does not exist", userId)));

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(String.format("Request with id %s does not exist", requestId)));

        Task taskToJoin = request.getTask();

        if (user.getTasks().size() > 3) {
            throw new ConditionViolationException(String.format("User %s already has more than 3 tasks, he is very busy", userId));
        }

        if (!request.getActive()) {
            throw new ConditionViolationException(String.format("Request with id %s is already inactive", requestId));
        }

        if (taskToJoin.getStatus().equals(Status.CANCELLED) ||
        taskToJoin.getStatus().equals(Status.DONE)) {
            throw new ConditionViolationException(String.format("Task %s has wrong status: DONE or CANCELED", taskToJoin.getId()));
        }


        taskToJoin.getUsers().add(user);
        request.setActive(false);

        Task taskSaved = taskRepository.saveAndFlush(taskToJoin);
        Request requestSaved = requestRepository.saveAndFlush(request);
        log.info("User {} answered the request {} and joined the task {}", user.getId(), requestId, taskSaved.getId());

        return modelToDto(taskSaved);

    }

    public void deleteRequest(Integer requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(String.format("Request with id '%s' does not exist", requestId)));

        requestRepository.deleteById(requestId);
        log.info("Request {} deleted", requestId);
    }

    public List<RequestDto> getAllActiveRequests() {
        List<Request> activeRequests = requestRepository.findAllByActiveIsTrue();

        log.info("Got all active requests");
        return activeRequests.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

}
