package com.vijay;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ModulithApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring context loads successfully
        // with all modules properly configured and dependencies resolved
    }

    @Test
    void applicationStartsSuccessfully() {
        // This test ensures the entire modulith application can start
        // and all modules are properly integrated
    }
}
