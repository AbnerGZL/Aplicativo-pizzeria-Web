package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Carrito;
import com.pizzeria.proyecto.Models.Paquete;
import com.pizzeria.proyecto.Models.Proprima;
import com.pizzeria.proyecto.Models.Proventa;
import com.pizzeria.proyecto.Repositories.PaqueteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class PaqueteService extends PaqueteRepository {

    public PaqueteService(WebClient webClient) {
        super(webClient);
    }

    public boolean postPaquete(Paquete paquete) {
        boolean result = post(paquete);
        if (result) {
            return true;
        }
        return false;
    }

    public Mono<List<Paquete>> getPaqueteByProvId(Integer id) {
        if (id != null) {
            return get()
                    .map(paquetes ->
                            paquetes.stream()
                                    .filter(paquete -> Objects.equals(paquete.getId_proventa(), id))
                                    .toList()
                    );
        } else {
            return Mono.just(List.of());
        }
    }
}
