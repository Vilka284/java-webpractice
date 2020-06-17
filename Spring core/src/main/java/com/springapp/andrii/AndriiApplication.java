package com.springapp.andrii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AndriiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndriiApplication.class, args);
    }

}
