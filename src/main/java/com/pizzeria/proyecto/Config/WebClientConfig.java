package com.pizzeria.proyecto.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        String token = "c6cf9d5275f5c1209ed66231d04e0a5cc17d8560";

        return builder
                .baseUrl("https://elote.pythonanywhere.com/api/")
                .defaultHeader("Authorization", "Token " + token)
                .build();
    }

}