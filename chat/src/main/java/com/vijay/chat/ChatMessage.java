package com.vijay.chat;

import com.vijay.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "chat_messages")
@EqualsAndHashCode(callSuper = true)
public class ChatMessage extends BaseEntity {
    
    @Column(name = "sender_id", nullable = false)
    private Long senderId;
    
    @Column(name = "receiver_id")
    private Long receiverId;
    
    @Column(name = "room_id")
    private String roomId;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Enumerated(EnumType.STRING)
    private MessageType type = MessageType.TEXT;
    
    @Column(name = "is_read")
    private boolean isRead = false;
    
    public enum MessageType {
        TEXT, IMAGE, FILE, SYSTEM
    }
}
