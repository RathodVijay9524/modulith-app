package com.vijay.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    List<ChatMessage> findByRoomIdOrderByCreatedAtAsc(String roomId);
    
    @Query("SELECT cm FROM ChatMessage cm WHERE " +
           "(cm.senderId = :senderId AND cm.receiverId = :receiverId) OR " +
           "(cm.senderId = :receiverId AND cm.receiverId = :senderId) " +
           "ORDER BY cm.createdAt ASC")
    List<ChatMessage> findMessagesBetweenUsers(@Param("senderId") Long senderId, 
                                              @Param("receiverId") Long receiverId);
    
    List<ChatMessage> findByReceiverIdAndIsReadFalse(Long receiverId);
    
    long countByReceiverIdAndIsReadFalse(Long receiverId);
}
