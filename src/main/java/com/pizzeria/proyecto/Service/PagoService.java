package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Carrito;
import com.pizzeria.proyecto.Models.Pago;
import com.pizzeria.proyecto.Repositories.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PagoService extends PagoRepository {


    public PagoService(WebClient webClient) {
        super(webClient);
    }

    public boolean postPago(Pago pago) {
        boolean result = post(pago);
        if(result) {
            return true;
        }
        return false;
    }
}
