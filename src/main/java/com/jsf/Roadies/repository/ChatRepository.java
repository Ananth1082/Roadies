package com.jsf.Roadies.repository;

import com.jsf.Roadies.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
