package com.pizzeria.proyecto.Repositories;

import com.pizzeria.proyecto.Models.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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

    public boolean put(Cliente cliente) {
        try {
            HttpStatus status = (HttpStatus) webClient.put()
                    .uri("clientes")
                    .bodyValue(cliente)
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
