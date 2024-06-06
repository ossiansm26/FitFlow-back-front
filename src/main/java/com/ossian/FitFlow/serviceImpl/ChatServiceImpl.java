package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.dto.MessageDTO;
import com.ossian.FitFlow.dto.UserDTO;
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
                .filter(chat -> chat.getParticipants().contains(user))
                .collect(Collectors.toList());
    }

    @Override
    public void sendMessage(MessageDTO privateMessage) {
        User sender = userRepository.findById(privateMessage.getSenderId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        User recipient = userRepository.findById(privateMessage.getRecipientId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Chat chat = findOrCreateChat(sender, recipient);

        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(privateMessage.getContent());
        message.setTimestamp(privateMessage.getTimestamp());

        messageRepository.save(message);

        MessageDTO messageDTO = convertToDto(message);

        messagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), messageDTO);
        messagingTemplate.convertAndSendToUser(recipient.getName(), "/queue/messages", messageDTO);
    }
    @Override
    public List<Message> getMessagesFromChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        return chat.getMessages();
    }

    @Override
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @Override
    public Chat createChat(Long userId, Long recipientId) {
        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Chat chat = new Chat();
        chat.getParticipants().add(sender);
        chat.getParticipants().add(recipient);
        return chatRepository.save(chat);
    }

    @Override
    public Chat findOrCreateChat(User sender, User recipient) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Chat> chats = chatRepository.findChatByParticipants(sender, recipient, pageable);
        System.out.println(sender.getName());
        if (!chats.isEmpty()) {
            return chats.get(0);
        } else {
            Chat newChat = new Chat();
            newChat.getParticipants().add(sender);
            newChat.getParticipants().add(recipient);
            return chatRepository.save(newChat);
        }
    }

    private MessageDTO convertToDto(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());

        UserDTO senderDto = new UserDTO();
        senderDto.setId(message.getSender().getId());
        senderDto.setName(message.getSender().getName());
        dto.setSender(senderDto);

        UserDTO recipientDto = new UserDTO();
        recipientDto.setId(message.getRecipient().getId());
        recipientDto.setName(message.getRecipient().getName());
        dto.setRecipient(recipientDto);

        return dto;
    }


}




