package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Repertorio;
import com.pizzeria.proyecto.Repositories.RepertorioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class RepertorioService extends RepertorioRepository {

    public RepertorioService(WebClient webClient) {
        super(webClient);
    }

    public Mono<Repertorio> getRepertorioById(Integer id) {
        if (id != null) {
            return get()
                    .flatMap(repertorios ->
                            Mono.justOrEmpty(repertorios.stream()
                                    .filter(repertorio -> Objects.equals(repertorio.getId_repertorio(),id))
                                    .findFirst()
                            ));
        } else {
            return Mono.empty();
        }
    }

}
