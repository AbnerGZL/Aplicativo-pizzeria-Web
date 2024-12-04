package com.pizzeria.proyecto.Repositories;

import com.pizzeria.proyecto.Models.Cliente;
import com.pizzeria.proyecto.Models.Proprima;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProprimaRepository {

    private final WebClient webClient;

    public Mono<List<Proprima>> get() {
        return webClient.get()
                .uri("productos-prima")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Proprima>>() {});
    }
}
