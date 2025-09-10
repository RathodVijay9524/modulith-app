package com.vijay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.modulith.Modulith;

@Modulith
@SpringBootApplication
@EnableJpaAuditing
/*@EntityScan(basePackages = {
        "com.vijay.common",
        "com.vijay.idgeneration",
        "com.vijay.modulith.hotelordering.domain.entity",
        "com.vijay.usermgmt",
        "com.vijay.audit",
        "com.vijay.chat",
        "com.vijay.payments"
})
@EnableJpaRepositories(basePackages = {
        "com.vijay.idgeneration",
        "com.vijay.modulith.hotelordering.domain.repository",
        "com.vijay.usermgmt",
        "com.vijay.audit",
        "com.vijay.chat",
        "com.vijay.payments"
})*/
public class ModulithApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModulithApplication.class, args);
    }
}
