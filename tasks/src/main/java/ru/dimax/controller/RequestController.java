package ru.dimax.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimax.model.request.NewRequestDto;
import ru.dimax.model.request.Request;
import ru.dimax.model.request.RequestDto;
import ru.dimax.model.task.NewTaskRequest;
import ru.dimax.model.task.TaskDto;
import ru.dimax.service.RequestService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    @PostMapping()
    public ResponseEntity<RequestDto> createRequest(@Valid @RequestBody NewRequestDto request) {
        RequestDto requestDto = requestService.createRequest(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(requestDto);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteRequest(@PathVariable("requestId") Integer requestId) {
        requestService.deleteRequest(requestId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{requestId}/answer/{userId}")
    public ResponseEntity<TaskDto> answerRequest(@PathVariable("requestId") Integer requestId,
                                                    @PathVariable("userId") Integer userId) {
        TaskDto taskDto = requestService.answerToRequest(userId, requestId);

        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @GetMapping
    public ResponseEntity<List<RequestDto>> getActiveRequests() {
        List<RequestDto> requestDtos = requestService.getAllActiveRequests();

        return ResponseEntity.status(HttpStatus.OK).body(requestDtos);
    }
}
