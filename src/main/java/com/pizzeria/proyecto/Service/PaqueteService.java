package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Carrito;
import com.pizzeria.proyecto.Models.Paquete;
import com.pizzeria.proyecto.Repositories.PaqueteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
}
