package ru.stats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static ru.stats.StatMapper.*;

@Service
@Slf4j
public class StatService {

    private final StatRepository statRepository;

    @Autowired
    public StatService(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    public Integer getAccomplishedTasksNumber(Integer userId, LocalDateTime startDT, LocalDateTime endDT) {
        Integer tasksNumber = statRepository.getNumberOfUsersTasks(userId, startDT, endDT);
        log.info("User {} has done {} tasks", userId, tasksNumber);
        return tasksNumber;
    }

    public StatDto saveAccomplishedTask(StatDto statDto) {
        Stat stat = dtoToModel(statDto);
        Stat saved = statRepository.save(stat);
        log.info("Saved successfully executed task {} by user {}", stat.getTaskId(), stat.getUserId());
        return modelToDto(saved);
    }
}
