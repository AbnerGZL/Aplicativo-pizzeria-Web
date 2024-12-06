package com.pizzeria.proyecto.Repositories;

import com.pizzeria.proyecto.Models.Repertorio;
import com.pizzeria.proyecto.Models.RepertorioDetalle;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RepertorioRepository {

    private final WebClient webClient;

    public Mono<List<Repertorio>> obtenerRepertorios(String filtro) {
        Mono<List<Repertorio>> repertor = null;
        try {
            repertor = webClient.get()
                    .uri("repertorios")
                    .retrieve()
                    .bodyToMono(Repertorio[].class)
                    .map(Arrays::asList)
                    .map(repertorios -> repertorios.stream()
                            .filter(repertorio -> repertorio.getTipo_repertorio().equalsIgnoreCase(filtro))
//                            .limit(cantidad)
                            .toList());

            return repertor;

        } catch (Exception e){
            System.out.println(e.getMessage());
            return Mono.empty();
        }
    }

    public Mono<List<Repertorio>> get() {
        return webClient.get()
                .uri("repertorios")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Repertorio>>() {});
    }

}
