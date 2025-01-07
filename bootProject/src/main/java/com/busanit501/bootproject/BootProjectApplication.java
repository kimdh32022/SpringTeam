package com.busanit501.bootproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BootProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootProjectApplication.class, args);
    }

}
