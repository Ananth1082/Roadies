package com.jsf.Roadies.service.chat;

import com.jsf.Roadies.dto.ChatDTO;
import com.jsf.Roadies.model.Chat;
import com.jsf.Roadies.request.ChatRequest;

import java.util.List;

public interface IChatService {
    List<Chat> getSquadChats(Long squadId);
    void addChat(ChatRequest chat);
    void deleteChat(Long id,ChatRequest chat);
    ChatDTO convertChatToDto(Chat chat);
}
