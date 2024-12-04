package com.pizzeria.proyecto.Repositories;

import com.pizzeria.proyecto.Models.Carrito;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Repository
@RequiredArgsConstructor
public class CarritoRepository {

    private final WebClient webClient;

    public boolean post(Carrito carrito) {
        try {
            HttpStatus status = (HttpStatus) webClient.post()
                    .uri("carritos")
                    .bodyValue(carrito)
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
