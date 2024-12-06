package com.pizzeria.proyecto.Repositories;

import com.pizzeria.proyecto.Models.Proventa;
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
public class ProventaRepository {

    private final WebClient webClient;

    public boolean post(Proventa proventa) {
        try {
            HttpStatus status = (HttpStatus) webClient.post()
                    .uri("productos-venta")
                    .bodyValue(proventa)
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

    public Mono<List<Proventa>> get() {
        return webClient.get()
                .uri("productos-venta")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Proventa>>() {});
    }

    public boolean put(Proventa proventa) {
        try {
            HttpStatus status = (HttpStatus) webClient.put()
                    .uri("productos-venta")
                    .bodyValue(proventa)
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
}
