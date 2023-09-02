package ru.dimax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimax.model.Invite;

public interface InviteRepository extends JpaRepository<Invite, Integer> {
}
