package com.jacktan.ftxrabbitmqjava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("my-rabbitmq")
@Configuration
public class Config {
    @Profile("publisher")
    @Bean
    public Publisher publisher() {
        return new Publisher();
    }
}
