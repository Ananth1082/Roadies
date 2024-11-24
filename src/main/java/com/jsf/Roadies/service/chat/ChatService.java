package com.jsf.Roadies.service.chat;

import com.jsf.Roadies.Exceptions.NotAMemberException;
import com.jsf.Roadies.Exceptions.SquadNotFoundException;
import com.jsf.Roadies.Exceptions.UserNotFoundException;
import com.jsf.Roadies.dto.ChatDTO;
import com.jsf.Roadies.model.Chat;
import com.jsf.Roadies.model.UserSquad;
import com.jsf.Roadies.repository.ChatRepository;
import com.jsf.Roadies.repository.SquadRepository;
import com.jsf.Roadies.repository.UserRepository;
import com.jsf.Roadies.repository.UserSquadRepository;
import com.jsf.Roadies.request.ChatRequest;
import com.jsf.Roadies.service.squad.SquadService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {
    private final ChatRepository  chatRepository;
    private final UserSquadRepository userSquadRepository;
    private final ModelMapper modelMapper;
    private final SquadService squadService;
    private final UserRepository userRepository;
    private final SquadRepository squadRepository;

    @Override
    public List<Chat> getSquadChats(Long squadId) {
        return chatRepository.findBySquadId(squadId);
    }

    @Override
    public void addChat(ChatRequest chat) {
        if (userSquadRepository.existsByUser_IdAndSquad_Id(chat.getSenderId(), chat.getSquadId())){
            Chat c = new Chat();
            c.setMessage(chat.getMessage());
            c.setSquad(squadRepository.findById(chat.getSquadId()).orElseThrow(()-> new SquadNotFoundException("Squad not found")));
            c.setUser(userRepository.findById(chat.getSenderId()).orElseThrow(()-> new UserNotFoundException("User not found")));
            UserSquad us = userSquadRepository.findByUser_IdAndSquad_Id(chat.getSenderId(), chat.getSquadId())
                    .orElseThrow(()->new NotAMemberException("Not a member"));
            c.setUserSquad(us);
            chatRepository.save(c);
        } else {
            throw new NotAMemberException("Not a member of this squad");
        }
    }

    @Override
    public void deleteChat(Long chatId,ChatRequest req) {
        if (userSquadRepository.existsByUser_IdAndSquad_Id(req.getSenderId(), req.getSquadId())){
            chatRepository.deleteById(chatId);
        } else {
            throw new NotAMemberException("Not a member of this squad");
        }
    }

    @Override
    public ChatDTO convertChatToDto(Chat chat) {
        return modelMapper.map(chat, ChatDTO.class);
    }
}
