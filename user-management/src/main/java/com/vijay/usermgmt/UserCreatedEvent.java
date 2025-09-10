package com.vijay.usermgmt;

/**
 * Event published when a new user is created.
 * Other modules can listen to this event to perform their own operations.
 */
public record UserCreatedEvent(Long userId, String email) {
}
