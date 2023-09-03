package ru.dimax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimax.model.request.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByActiveIsTrue();

}
