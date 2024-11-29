package com.pizzeria.proyecto.Repositories;

import com.pizzeria.proyecto.Models.RepertorioDetalle;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RepertorioDetalleRepository {

    private final WebClient webClient;

    public Mono<List<RepertorioDetalle>> get() {
        return webClient.get()
                .uri("detalles-repertorio")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RepertorioDetalle>>() {});
    }
}
