package com.hymns.hymns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HymnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HymnsApplication.class, args);
    }

}
