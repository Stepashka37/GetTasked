package ru.dimax.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimax.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
