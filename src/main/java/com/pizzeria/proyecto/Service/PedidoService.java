package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Carrito;
import com.pizzeria.proyecto.Models.Pedido;
import com.pizzeria.proyecto.Repositories.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;


@Service
public class PedidoService extends PedidoRepository {

    public PedidoService(WebClient webClient) {
        super(webClient);
    }

    public boolean postPedido(Pedido pedido){
        boolean result = post(pedido);
        if(result) {
            return true;
        }
        return false;
    }

    public Mono<Pedido> getPedidoByCodigo(String codigo) {
        if (codigo != null) {
            return get()
                    .flatMap(pedidos ->
                            Mono.justOrEmpty(pedidos.stream()
                                    .filter(pedido -> Objects.equals(pedido.getCodigo(), codigo))
                                    .findFirst()
                            ));
        } else {
            return Mono.empty();
        }
    }
}