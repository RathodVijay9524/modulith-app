package com.vijay.audit;

import com.vijay.usermgmt.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event listener for audit logging in Spring Modulith
 * Listens to application events and creates audit log entries
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuditEventListener {
    
    private final AuditService auditService;
    
    // 🧑‍💼 User Management Events
    
    @EventListener
    public void onUserCreated(UserCreatedEvent event) {
        auditService.createAuditLog(
            "USER_CREATED",
            String.valueOf(event.userId()),
            "USER",
            String.format("User created with email: %s", event.email()),
            "system"
        );
        log.info("📓 Audit logged: User created - {}", event.email());
    }
    
    // 🏨 Hotel Ordering Events (we can add these as we create hotel events)
    
    // Example for future hotel booking events
    /*
    @EventListener
    public void onBookingCreated(BookingCreatedEvent event) {
        auditService.createAuditLog(
            "BOOKING_CREATED",
            event.bookingId(),
            "BOOKING",
            String.format("Booking created for user: %s, amount: %s", event.userId(), event.amount()),
            event.userEmail()
        );
        log.info("📓 Audit logged: Booking created - {}", event.bookingId());
    }
    */
    
    // 💳 Payment Events (we can add these as we create payment events)
    
    // Example for future payment events
    /*
    @EventListener
    public void onPaymentProcessed(PaymentProcessedEvent event) {
        auditService.createAuditLog(
            "PAYMENT_PROCESSED",
            event.paymentId(),
            "PAYMENT",
            String.format("Payment processed: %s for booking: %s", event.amount(), event.bookingId()),
            event.userEmail()
        );
        log.info("📓 Audit logged: Payment processed - {}", event.paymentId());
    }
    */
    
    // 💬 Chat Events (we can add these as we create chat events)
    
    // Example for future chat events
    /*
    @EventListener
    public void onMessageSent(MessageSentEvent event) {
        auditService.createAuditLog(
            "MESSAGE_SENT",
            event.messageId(),
            "MESSAGE",
            String.format("Message sent in chat: %s", event.chatId()),
            event.senderEmail()
        );
        log.info("📓 Audit logged: Message sent - {}", event.messageId());
    }
    */
    
    // 🔔 Notification Events (we can add these as we create notification events)
    
    // Example for future notification events
    /*
    @EventListener
    public void onNotificationSent(NotificationSentEvent event) {
        auditService.createAuditLog(
            "NOTIFICATION_SENT",
            event.notificationId(),
            "NOTIFICATION",
            String.format("Notification sent to: %s, type: %s", event.recipientEmail(), event.type()),
            "system"
        );
        log.info("📓 Audit logged: Notification sent - {}", event.notificationId());
    }
    */
}
