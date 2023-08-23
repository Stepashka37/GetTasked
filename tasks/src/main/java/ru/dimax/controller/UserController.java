package ru.dimax.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimax.model.NewUserRequest;
import ru.dimax.model.TaskDto;
import ru.dimax.model.UpdateUserRequest;
import ru.dimax.model.UserDto;
import ru.dimax.repository.UserRepository;
import ru.dimax.service.TaskService;
import ru.dimax.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody NewUserRequest request) {
        UserDto userDto = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UpdateUserRequest request, @PathVariable("userId") Integer userId) {
        UserDto userDto = userService.updateUserByAdmin(userId, request);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<TaskDto>> getCurrentUserTasks(@PathVariable("userId") Integer userId) {
        List<TaskDto> tasks = taskService.getAllCurrentUserTasks(userId);

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PutMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> assignUserToTask(@PathVariable Integer userId, @PathVariable Integer taskId) {
        TaskDto assignedTask = userService.assignUserToTask(userId, taskId);

        return ResponseEntity.status(HttpStatus.OK).body(assignedTask);
    }

    @GetMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> reportUponTaskCompletion(@PathVariable Integer userId, @PathVariable Integer taskId) {
        TaskDto completed = taskService.reportUponTaskCompletion(userId, taskId);

        return ResponseEntity.status(HttpStatus.OK).body(completed);
    }




}
