package ru.dimax.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimax.model.*;
import ru.dimax.service.TaskService;
import ru.dimax.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor

public class TaskController {

    private final TaskService taskService;

    private final UserService userService;

    @PostMapping("/{userId}")
    @Tag(name = "Admin Tasks", description = "Admin requests for tasks")
    @Operation(
            summary = "Create new task",
            description = "Endpoint creates new task. Can only be created by the admin"
    )
    public ResponseEntity<TaskDto> createTask(@PathVariable("userId") Integer userId, @Valid @RequestBody NewTaskRequest request) {
        TaskDto taskDto = taskService.createTask(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
    }

    @PatchMapping("/{taskId}/update")
    @Tag(name = "Admin Tasks", description = "Admin requests for tasks")
    @Operation(
            summary = "Update task by admin",
            description = "Endpoint updates existing task"
    )
    public ResponseEntity<TaskDto> updateTaskByAdmin(@PathVariable("taskId") Integer taskId,
                                                     @Valid @RequestBody UpdateTaskRequest request) {
        TaskDto taskDto = taskService.updateTaskByAdmin(taskId, request);

        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @PatchMapping("/{taskId}")
    @Tag(name = "Admin Tasks", description = "Admin requests for tasks")
    @Operation(
            summary = "Change task status",
            description = "Endpoint switches task from status PENDING to either IN_PROGRESS or CANCELLED"
    )
    public ResponseEntity<TaskDto> approveTask(@RequestParam(value = "approve", defaultValue = "true") Boolean approve,
                                                     @PathVariable("taskId") Integer taskId) {
        TaskDto taskDto = taskService.approveTask(approve, taskId);
        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @GetMapping("/{taskId}")
    @Tag(name = "Admin Tasks", description = "Admin requests for tasks")
    @Operation(
            summary = "Get task info",
            description = "Endpoint retrieves info about task"
    )
    public ResponseEntity<TaskDto> getTask(@PathVariable("taskId") Integer taskId) {
        TaskDto taskDto = taskService.getTask(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }


}
