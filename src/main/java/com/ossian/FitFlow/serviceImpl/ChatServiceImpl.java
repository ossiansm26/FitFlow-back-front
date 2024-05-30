package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.model.Chat;
import com.ossian.FitFlow.model.Message;
import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.repository.ChatRepository;
import com.ossian.FitFlow.repository.UserRepository;
import com.ossian.FitFlow.service.ChatService;
import com.ossian.FitFlow.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public List<Chat> getAllChatsFromUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return chatRepository.findAll()
                .stream()
                .filter(participants -> participants.getParticipants().contains(user))
                .collect(Collectors.toList());
    }

    @Override
    public void sendMessage(Message privateMessage) {
        User sender = userRepository.findById(privateMessage.getSender().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User recipient = userRepository.findById(privateMessage.getRecipient().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String content = privateMessage.getContent();

        Chat chat = findOrCreateChat(sender, recipient);

        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setTimestamp(new Date());

        messageRepository.save(message);
        messagingTemplate.convertAndSendToUser(recipient.getName(), "/queue/messages", message);
    }

    @Override
    public Chat findOrCreateChat(User sender, User recipient) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Chat> chats = chatRepository.findChatByParticipants(sender, recipient, pageable);
        return chats.isEmpty() ? null : chats.get(0);
    }
}




