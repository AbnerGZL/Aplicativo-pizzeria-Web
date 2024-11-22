package com.pizzeria.proyecto.Repositories;

import com.pizzeria.proyecto.Models.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClienteRepository {

    private final WebClient webClient;

    public Mono<List<Cliente>> get() {
        return webClient.get()
            .uri("clientes")
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<Cliente>>() {});
    }
}
