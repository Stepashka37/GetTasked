package ru.dimax.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.dimax.model.invite.InviteDto;
import ru.dimax.model.invite.NewInviteDto;
import ru.dimax.model.task.TaskDto;
import ru.dimax.model.user.FullUserDto;
import ru.dimax.model.user.NewUserRequest;
import ru.dimax.model.user.UpdateUserRequest;
import ru.dimax.model.user.UserDto;
import ru.dimax.service.InviteService;
import ru.dimax.service.TaskService;
import ru.dimax.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;
    private final InviteService inviteService;

    public UserController(UserService userService, TaskService taskService, InviteService inviteService) {
        this.userService = userService;
        this.taskService = taskService;
        this.inviteService = inviteService;
    }

    @PostMapping
    @Tag(name = "Admin Users", description = "Admin requests for users")
    @Operation(
            summary = "Create new user",
            description = "Endpoint creates new user. Can only be created by the admin"
    )
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody NewUserRequest request) {
        UserDto userDto = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PatchMapping("/{userId}")
    @Tag(name = "Admin Users", description = "Admin requests for users")
    @Operation(
            summary = "Update user",
            description = "Endpoint updates existing user"
    )
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UpdateUserRequest request, @PathVariable("userId") Integer userId) {
        UserDto userDto = userService.updateUserByAdmin(userId, request);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/{userId}")
    @Tag(name = "Admin Users", description = "Admin requests for users")
    @Operation(
            summary = "Get user info",
            description = "Endpoint retrieves info about the current user"
    )
    public ResponseEntity<FullUserDto> getUser(@PathVariable("userId") Integer userId) {
        FullUserDto fullUser = userService.getUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(fullUser);
    }

    @DeleteMapping("/{userId}")
    @Tag(name = "Admin Users", description = "Admin requests for users")
    @Operation(
            summary = "Delete user",
            description = "Endpoint deletes current user"
    )
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/tasks")
    @Tag(name = "User Users", description = "User requests for users")
    @Operation(
            summary = "Get tasks being executed",
            description = "Endpoint retrieves tasks that are being executed by the current user"
    )
    public ResponseEntity<List<TaskDto>> getCurrentUserTasks(@PathVariable("userId") Integer userId) {
        List<TaskDto> tasks = taskService.getAllCurrentUserTasks(userId);

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PutMapping("/{userId}/tasks/{taskId}/responsible")
    @Tag(name = "Admin Users", description = "Admin requests for users")
    @Operation(
            summary = "Make user responsible",
            description = "Endpoint makes user responsible for the current task"
    )
    public ResponseEntity<TaskDto> setUserResponsible(@PathVariable Integer userId, @PathVariable Integer taskId) {
        TaskDto assignedTask = userService.setUserResponsible(userId, taskId);

        return ResponseEntity.status(HttpStatus.OK).body(assignedTask);
    }

    @GetMapping("/{userId}/tasks/{taskId}")
    @Tag(name = "User Users", description = "User requests for users")
    @Operation(
            summary = "Mark task as completed",
            description = "Endpoint marks task as completed. Can be done only by the responsible user"
    )
    public ResponseEntity<TaskDto> reportUponTaskCompletion(@PathVariable Integer userId, @PathVariable Integer taskId) {
        TaskDto completed = taskService.reportUponTaskCompletion(userId, taskId);

        return ResponseEntity.status(HttpStatus.OK).body(completed);
    }

    @PutMapping("/{userId}/tasks/assign/{taskId}")
    @Tag(name = "Admin Users", description = "Admin requests for users")
    @Operation(
            summary = "Assign user to task",
            description = "Endpoint assign user for current task"
    )
    public ResponseEntity<TaskDto> assignUserToTask(@PathVariable Integer userId, @PathVariable Integer taskId) {
        TaskDto updatedTask = userService.assignUserToTask(userId, taskId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }

    @PutMapping("/{userId}/tasks/remove/{taskId}")
    @Tag(name = "Admin Users", description = "Admin requests for users")
    @Operation(
            summary = "Remove user from task",
            description = "Endpoint remove user from current task"
    )
    public ResponseEntity<TaskDto> removeUserFromTask(@PathVariable Integer userId, @PathVariable Integer taskId) {
        TaskDto updatedTask = userService.removeUserFromTask(userId, taskId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }



    @PostMapping("/{userId}/invites/{taskId}/send/{secondUserId}")
    @Tag(name = "User Users", description = "User requests for users")
    @Operation(
            summary = "Invite user to task",
            description = "Endpoint invite user for particular task"
    )
    public ResponseEntity<InviteDto> inviteUserToTask(@PathVariable Integer userId,
                                                      @PathVariable Integer taskId ,
                                                      @PathVariable Integer secondUserId,
                                                      @Validated @RequestBody NewInviteDto dto) {
        InviteDto inviteDto = inviteService.inviteUserToTask(userId, taskId, secondUserId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(inviteDto);
    }

    @GetMapping("/{userId}/invites/{inviteId}/inbox")
    @Tag(name = "User Users", description = "User requests for users")
    @Operation(
            summary = "Accept or refuse invite to task",
            description = "Endpoint to accept or refuse for particular task"
    )
    public ResponseEntity<TaskDto> answerToInvite(@PathVariable Integer userId,
                                                      @PathVariable Integer inviteId,
                                                      @RequestParam("accept") Boolean accepted) {
        TaskDto taskDto = inviteService.answerToInvite(userId, inviteId, accepted);

        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }




}
