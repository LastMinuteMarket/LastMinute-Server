package com.lastminute.lastminuteserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LastMinuteServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LastMinuteServerApplication.class, args);
    }

}
