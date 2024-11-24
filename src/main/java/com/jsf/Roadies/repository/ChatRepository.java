package com.jsf.Roadies.repository;

import com.jsf.Roadies.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findBySquadId(Long squadId);
}
