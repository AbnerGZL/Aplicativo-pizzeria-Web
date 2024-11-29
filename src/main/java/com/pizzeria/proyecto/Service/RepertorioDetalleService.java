package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Cliente;
import com.pizzeria.proyecto.Models.RepertorioDetalle;
import com.pizzeria.proyecto.Repositories.RepertorioDetalleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RepertorioDetalleService extends RepertorioDetalleRepository {

    public RepertorioDetalleService(WebClient webClient) {
        super(webClient);
    }

    public Mono<List<RepertorioDetalle>> getDetalles(String id) {
        if (id != null) {
            return get()
                    .map(detalles ->
                            detalles.stream()
                                    .filter(detalle -> detalle.getId_repertorio().equals(id))
                                    .toList()
                    );
        } else {
            return Mono.just(List.of());
        }
    }
}
