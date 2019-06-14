package com.nikola3in1.audiobooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.nikola3in1")
@EnableJpaRepositories("com.nikola3in1.repository")
@EntityScan("com.nikola3in1.model")
public class AudiobooksBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudiobooksBackendApplication.class, args);
    }

}
