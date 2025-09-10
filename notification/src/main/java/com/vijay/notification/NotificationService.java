package com.vijay.notification;

import com.vijay.usermgmt.UserCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    public void sendEmail(String to, String subject, String body) {
        // Email sending logic here
        System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
    
    public void sendSMS(String phoneNumber, String message) {
        // SMS sending logic here
        System.out.println("Sending SMS to: " + phoneNumber);
        System.out.println("Message: " + message);
    }
    
    @EventListener
    public void handleUserCreated(UserCreatedEvent event) {
        // Send welcome email to new user
        sendEmail(event.email(), 
                 "Welcome to Our Platform!", 
                 "Thank you for joining us. We're excited to have you on board!");
    }
    

}
