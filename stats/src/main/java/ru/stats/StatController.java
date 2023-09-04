package ru.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    @GetMapping("/{userId}")
    public ResponseEntity<Integer> getAccomplishedTasksNumber(@PathVariable("userId") Integer userId,
                                                     @RequestParam(required = false) String rangeStart,
                                                     @RequestParam(required = false) String rangeEnd) {
        LocalDateTime startDT = LocalDateTime.parse(rangeStart);
        LocalDateTime endDT = LocalDateTime.parse(rangeEnd);

        Integer accomplishedTasks = statService.getAccomplishedTasksNumber(userId, startDT, endDT);
        return ResponseEntity.status(HttpStatus.OK).body(accomplishedTasks);
    }

    @PostMapping
    public ResponseEntity<StatDto> saveAccomplishedTask(@Valid @RequestBody StatDto statDto) {
        StatDto savedStat = statService.saveAccomplishedTask(statDto);
        return ResponseEntity.status(HttpStatus.OK).body(savedStat);
    }


}
