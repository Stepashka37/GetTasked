package ru.dimax.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dimax.exceptions.ConditionViolationException;
import ru.dimax.exceptions.InvitationNotFoundException;
import ru.dimax.exceptions.TaskNotFoundException;
import ru.dimax.exceptions.UserNotFoundException;
import ru.dimax.model.*;
import ru.dimax.repository.InviteRepository;
import ru.dimax.repository.TaskRepository;
import ru.dimax.repository.UserRepository;

import static ru.dimax.mapper.InviteMapper.*;
import static ru.dimax.mapper.TaskMapper.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class InviteService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final InviteRepository inviteRepository;

    public InviteDto inviteUserToTask(Integer userId, Integer taskId, Integer secondUserId, NewInviteDto dto) {
        User user1 = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        User user2 = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id '%s' does not exist", taskId)));

        if (user1.getGrade() == Grade.JUNIOR) {
            throw new ConditionViolationException("Junior developers are not allowed to invite team members");

        }

        if (user2.getTasks().size() > 3) {
            throw new ConditionViolationException(String.format("User with id '%s' is already executing 3 tasks", user2.getId()));
        }

        if (task.getStatus() == Status.DONE || task.getStatus() == Status.CANCELLED) {
            throw new ConditionViolationException("You cannot invite user to task that is already DONE or CANCELLED");
        }

        Invite invite = Invite.builder()
                .task(task)
                .inviter(user1)
                .invited(user2)
                .comment(dto.getComment())
                .accepted(false)
                .build();

        Invite saved = inviteRepository.save(invite);
        log.info("Invite {} saved", saved.getId());

        return modelToDto(saved);
    }

    public TaskDto answerToInvite(Integer userId, Integer inviteId, Boolean accepted) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' does not exist", userId)));

        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new InvitationNotFoundException(String.format("Invite with id '%s' does not exist", inviteId)));

        Task task = taskRepository.findById(invite.getTask().getId())
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id '%s' does not exist", invite.getTask().getId())));

        if (task.getStatus() == Status.DONE || task.getStatus() == Status.CANCELLED) {
            throw new ConditionViolationException("You cannot invite user to task that is already DONE or CANCELLED");
        }

        if (accepted) {
            task.getUsers().add(user);
            log.info("User joined the task");
        }

        Task saved = taskRepository.saveAndFlush(task);

        return modelToDto(saved);

    }
}
