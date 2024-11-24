package com.jsf.Roadies.controller;

import com.jsf.Roadies.dto.ChatDTO;
import com.jsf.Roadies.request.ChatRequest;
import com.jsf.Roadies.response.ApiResponse;
import com.jsf.Roadies.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/chats")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("squad/{squad-id}")
    public ResponseEntity<ApiResponse> getSuadChat(@PathVariable("squad-id") Long squadId) {
        try {
            List<ChatDTO> chats = chatService.getSquadChats(squadId)
                    .stream()
                    .map(chatService::convertChatToDto)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("success", chats));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createChat(@RequestBody ChatRequest req) {
        try {
            chatService.addChat(req);
            return ResponseEntity.ok(new ApiResponse("success",null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @DeleteMapping("{chatId}")
    public ResponseEntity<ApiResponse> deleteChat(@PathVariable("chatId") Long chatId, @RequestBody ChatRequest req) {
        try {
            chatService.deleteChat(chatId,req);
            return ResponseEntity.ok(new ApiResponse("success",null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

}
