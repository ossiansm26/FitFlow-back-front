package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.dto.MessageDTO;
import com.ossian.FitFlow.model.Chat;
import com.ossian.FitFlow.model.Message;
import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.serviceImpl.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import java.util.List;

@RestController
@RequestMapping("api/chat")
@CrossOrigin(origins = "http://localhost:8080")
public class ChatController {
    @Autowired
    private ChatServiceImpl chatService;


    @MessageMapping("/chat.sendMessage")
    public void sendMessage(MessageDTO privateMessage) {
        chatService.sendMessage(privateMessage);
    }

    @GetMapping("/getChats/{userId}")
    public List<Chat> getChats(@PathVariable Long userId) {
        return chatService.getAllChatsFromUser(userId);
    }
    @GetMapping("/chat.getMessages/{chatId}")
    public List<Message> getMessages(@PathVariable Long chatId) {
        return chatService.getMessagesFromChat(chatId);
    }
    @PostMapping("/createChat/{userId}/{recipientId}")
    public Chat createChat(@PathVariable Long userId, @PathVariable Long recipientId) {
        return chatService.createChat(userId, recipientId);
    }
    @GetMapping("/allChats")
    public List<Chat> getAllChats() {
        return chatService.getAllChats();
    }


}
