package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Carrito;
import com.pizzeria.proyecto.Repositories.CarritoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
}
