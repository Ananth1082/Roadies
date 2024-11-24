package com.jsf.Roadies.service.chat;

import com.jsf.Roadies.model.Chat;

import java.util.List;

public interface IChatService {
    List<Chat> getSquadChats(Long squadId);
    void addChat(Chat chat);
    void deleteChat(Chat chat);
}
