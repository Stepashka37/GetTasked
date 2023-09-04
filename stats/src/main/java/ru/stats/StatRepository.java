package ru.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


public interface StatRepository extends JpaRepository<Stat, Integer> {

    @Query("select count(s) from Stat s where s.userId = :userId and s.finishTime  between :startTime and :finishTime")
    Integer getNumberOfUsersTasks(@Param("userId") Integer userId,
                                  @Param("startTime") LocalDateTime startTime,
                                  @Param("finishTime") LocalDateTime finishTime);
}
