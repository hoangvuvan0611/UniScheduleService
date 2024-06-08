package com.service.unischeduleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class UniScheduleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniScheduleServiceApplication.class, args);
    }

}
