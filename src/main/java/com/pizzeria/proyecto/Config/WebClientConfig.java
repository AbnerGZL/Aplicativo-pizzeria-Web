package com.pizzeria.proyecto.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${api.token}")
    private String token;
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://elote.pythonanywhere.com/api/")
                .defaultHeader("Authorization", "Token " + token)
                .build();
    }

}