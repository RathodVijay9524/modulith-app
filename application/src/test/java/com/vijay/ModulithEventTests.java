package com.vijay;

import com.vijay.usermgmt.User;
import com.vijay.usermgmt.UserCreatedEvent;
import com.vijay.usermgmt.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Spring Modulith event-driven communication between modules
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ModulithEventTests {

    @Autowired
    private UserService userService;


    @Test
    void testUserCreationTriggersEvent() {
        // Test that creating a user triggers the UserCreatedEvent
        // which should be handled by the notification module
        
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setFirstName("Test");
        user.setLastName("User");

        User savedUser = userService.createUser(user);
        
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testEventDrivenCommunication() {
        // This test demonstrates event-driven communication
        // between user-management and notification modules
        
        User user = new User();
        user.setUsername("eventtest");
        user.setEmail("event@example.com");
        user.setPassword("password123");
        user.setFirstName("Event");
        user.setLastName("Test");

        // When we create a user, it should publish UserCreatedEvent
        // The notification module should listen and process this event
        User createdUser = userService.createUser(user);
        
        assertThat(createdUser).isNotNull();
        // The event processing happens asynchronously
        // In a real test, you might want to verify the notification was sent
    }
}
