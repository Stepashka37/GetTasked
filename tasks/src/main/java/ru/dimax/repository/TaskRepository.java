package ru.dimax.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dimax.model.task.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t join t.users u where u.id = :userId order by t.deadline asc")
    List<Task> findTasksByUserId(@Param("userId") Integer userId);

}
