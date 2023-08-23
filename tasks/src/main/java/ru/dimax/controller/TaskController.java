package ru.dimax.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dimax.service.TaskService;
import ru.dimax.service.UserService;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    //private final TaskService taskService;
    //private final UserService userService;

}
