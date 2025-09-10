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



}
