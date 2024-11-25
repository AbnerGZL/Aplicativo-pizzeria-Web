package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Repertorio;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class RepertorioService {

    private final WebClient webClient;

    public RepertorioService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<Repertorio>> obtenerRepertorios(String filtro, Integer cantidad) {
        return webClient.get()
                .uri("/repertorios")
                .retrieve()
                .bodyToMono(Repertorio[].class)
                .map(Arrays::asList)
                .map(repertorios -> repertorios.stream()
                        .filter(repertorio -> repertorio.getTipo_repertorio().equalsIgnoreCase(filtro))
                        .limit(cantidad)
                        .toList());
    }

}
