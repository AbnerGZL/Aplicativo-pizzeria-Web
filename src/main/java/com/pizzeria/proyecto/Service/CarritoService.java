package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Carrito;
import com.pizzeria.proyecto.Models.RepertorioDetalle;
import com.pizzeria.proyecto.Repositories.CarritoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class CarritoService extends CarritoRepository {

    public CarritoService(WebClient webClient) {
        super(webClient);
    }

    public boolean postCarrito(Carrito carrito) {
        boolean result = post(carrito);
        if(result) {
            return true;
        }
        return false;
    }

    public Mono<List<Carrito>> getCarritosByUserId(Integer id) {
        if (id != null) {
            return get()
                    .map(carritos ->
                            carritos.stream()
                                    .filter(carrito -> Objects.equals(carrito.getId_cliente(),id))
                                    .toList()
                    );
        } else {
            return Mono.just(List.of());
        }
    }
}
