package com.jsf.Roadies.service.chat;

import com.jsf.Roadies.Exceptions.NotAMemberException;
import com.jsf.Roadies.model.Chat;
import com.jsf.Roadies.repository.ChatRepository;
import com.jsf.Roadies.repository.UserSquadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {
    private final ChatRepository  chatRepository;
    private final UserSquadRepository userSquadRepository;

    @Override
    public List<Chat> getSquadChats(Long squadId) {
        return chatRepository.findBySquadId(squadId);
    }

    @Override
    public void addChat(Chat chat) {
        if (userSquadRepository.existsByUser_IdAndSquad_Id(chat.getSenderId(), chat.getSquadId())){
            chatRepository.save(chat);
        } else {
            throw new NotAMemberException("Not a member of this squad");
        }
    }

    @Override
    public void deleteChat(Chat chat) {
        if (userSquadRepository.existsByUser_IdAndSquad_Id(chat.getSenderId(), chat.getSquadId())){
            chatRepository.delete(chat);
        } else {
            throw new NotAMemberException("Not a member of this squad");
        }
    }
}
