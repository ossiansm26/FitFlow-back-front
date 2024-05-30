package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.Chat;
import com.ossian.FitFlow.model.Message;
import com.ossian.FitFlow.model.User;

import java.util.List;

public interface ChatService {
    Message saveMessage(Message message);
    List<Chat> getAllChatsFromUser(Long userId);
    void deleteMessage(Long id);
    Chat findOrCreateChat(User sender, User recipient);


    void sendMessage(Message privateMessage);
}
