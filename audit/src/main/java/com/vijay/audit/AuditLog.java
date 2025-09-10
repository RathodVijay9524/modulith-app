package com.vijay.audit;

import com.vijay.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity for storing audit trail records
 * Tracks all system events and user actions for compliance and monitoring
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "audit_logs")
public class AuditLog extends BaseEntity {
    
    @Column(nullable = false)
    private String action; // e.g., "USER_CREATED", "BOOKING_CREATED", "PAYMENT_PROCESSED"
    
    @Column(nullable = false)
    private String entityId; // ID of the entity that was affected
    
    @Column(columnDefinition = "TEXT")
    private String details; // JSON or detailed description of the event
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Column(nullable = false)
    private String performedBy; // User email or system identifier
    
    @Column
    private String entityType; // e.g., "USER", "BOOKING", "PAYMENT"
    
    @Column
    private String ipAddress; // Optional: track IP address for security
    
    @Column
    private String userAgent; // Optional: track user agent for security
    
    // Constructor for easy creation
    public AuditLog(String action, String entityId, String entityType, String details, String performedBy) {
        this.action = action;
        this.entityId = entityId;
        this.entityType = entityType;
        this.details = details;
        this.performedBy = performedBy;
        this.timestamp = LocalDateTime.now();
    }
}
