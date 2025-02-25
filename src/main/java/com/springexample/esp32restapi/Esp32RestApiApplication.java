package com.springexample.esp32restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class Esp32RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Esp32RestApiApplication.class, args);
    }

}
