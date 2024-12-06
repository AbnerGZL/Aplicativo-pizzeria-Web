package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Carrito;
import com.pizzeria.proyecto.Models.DetallePedido;
import com.pizzeria.proyecto.Repositories.DetallePedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DetallePedidoService extends DetallePedidoRepository {

    public DetallePedidoService(WebClient webClient) {
        super(webClient);
    }

    public boolean postDetalle(DetallePedido detalle) {
        boolean result = post(detalle);
        if(result) {
            return true;
        }
        return false;
    }
}
