package ru.dimax.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dimax.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t JOIN t.userTasks ut JOIN ut.user u WHERE u.id = :userId")
    List<Task> findTasksByUserId(@Param("userId") Integer userId);

}
