package com.vijay;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

/**
 * Tests for Spring Modulith architecture validation and documentation
 */
class ModulithArchitectureTests {

    ApplicationModules modules = ApplicationModules.of(ModulithApplication.class);

    @Test
    void verifiesModularStructure() {
        // This test verifies that the application follows proper modulith structure
        // and that all modules are correctly detected and configured
        modules.verify();
    }

    @Test
    void createModuleDocumentation() throws Exception {
        // This test generates documentation for the modulith structure
        // including module dependencies and boundaries
        new Documenter(modules)
                .writeDocumentation()
                .writeIndividualModulesAsPlantUml();
    }

    @Test
    void showModuleStructure() {
        // This test prints the detected module structure for verification
        modules.forEach(System.out::println);
    }
}
