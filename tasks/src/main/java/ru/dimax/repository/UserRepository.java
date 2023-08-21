package ru.dimax.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dimax.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u join u.tasks t where t.id = :taskId")
    List<User> getUsersExecutingTask(@Param("taskId") Integer taskId);
}
