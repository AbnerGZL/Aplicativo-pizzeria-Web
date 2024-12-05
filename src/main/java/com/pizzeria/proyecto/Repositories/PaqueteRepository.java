package com.pizzeria.proyecto.Repositories;

import com.pizzeria.proyecto.Models.Carrito;
import com.pizzeria.proyecto.Models.Paquete;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaqueteRepository {

    private final WebClient webClient;

    public boolean post(Paquete paquete) {
        try {
            HttpStatus status = (HttpStatus) webClient.post()
                    .uri("paquetes")
                    .bodyValue(paquete)
                    .retrieve()
                    .toBodilessEntity()
                    .block()
                    .getStatusCode();

            return status.is2xxSuccessful();
        } catch (WebClientResponseException e) {
            System.out.println("Error de cliente/servidor: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return false;
        }
    }

    public Mono<List<Paquete>> get() {
        return webClient.get()
                .uri("paquetes")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Paquete>>() {});
    }
}
