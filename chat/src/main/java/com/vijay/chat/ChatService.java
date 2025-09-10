package com.vijay.chat;

import com.vijay.usermgmt.UserCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChatService {
    
    private final ChatMessageRepository chatMessageRepository;
    
    @Autowired
    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }
    
    public ChatMessage sendMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }
    
    public List<ChatMessage> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        return chatMessageRepository.findMessagesBetweenUsers(senderId, receiverId);
    }
    
    public List<ChatMessage> getRoomMessages(String roomId) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
    }
    
    public void markMessageAsRead(Long messageId) {
        chatMessageRepository.findById(messageId)
            .ifPresent(message -> {
                message.setRead(true);
                chatMessageRepository.save(message);
            });
    }
    
    // Example of listening to events from other modules
    @EventListener
    public void handleUserCreated(UserCreatedEvent event) {
        // Send welcome message to new user
        ChatMessage welcomeMessage = new ChatMessage();
        welcomeMessage.setSenderId(1L); // System user
        welcomeMessage.setReceiverId(event.userId());
        welcomeMessage.setContent("Welcome to our platform! Feel free to start chatting.");
        welcomeMessage.setType(ChatMessage.MessageType.SYSTEM);
        
        sendMessage(welcomeMessage);
    }
}
