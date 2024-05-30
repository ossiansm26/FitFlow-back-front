package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.model.Chat;
import com.ossian.FitFlow.model.Message;
import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.serviceImpl.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.MessageMapping;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:8080")
public class ChatController {
    @Autowired
    private ChatServiceImpl chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Message privateMessage) {
        System.out.println("Message sent");
       chatService.sendMessage(privateMessage);
    }
    @MessageMapping("/chat.getChats/{userId}")
    public List<Chat> getChats(@PathVariable Long userId) {
        return chatService.getAllChatsFromUser(userId);

    }


}
