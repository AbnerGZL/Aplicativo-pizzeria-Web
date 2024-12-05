package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Proprima;
import com.pizzeria.proyecto.Repositories.ProprimaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class ProprimaService extends ProprimaRepository {

    public ProprimaService(WebClient webClient) {
        super(webClient);
    }

    public Mono<Proprima> getProprimaByName(String nombre, Integer tamaño) {
        if (nombre != null && tamaño != null) {
            return get()
                    .flatMap(proprimas ->
                            Mono.justOrEmpty(proprimas.stream()
                                    .filter(proprima -> proprima.getNombre().equals(nombre))
                                    .filter(proprima -> proprima.getTamano().equals(tamaño))
                                    .findFirst()
                            ));
        } else {
            return Mono.empty();
        }
    }

    public Mono<Proprima> getProprimaById(Integer id) {
        if (id != null) {
            return get()
                    .flatMap(proprimas ->
                            Mono.justOrEmpty(proprimas.stream()
                                    .filter(proprima -> Objects.equals(proprima.getId_proprima(), id))
                                    .findFirst()
                            ));
        } else {
            return Mono.empty();
        }
    }
}
