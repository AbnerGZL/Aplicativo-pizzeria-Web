package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Cliente;
import com.pizzeria.proyecto.Repositories.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ClienteService extends ClienteRepository {

    public ClienteService(WebClient webClient) {
        super(webClient);
    }

    public Mono<Cliente> getCliente(String nombre) {
        if (nombre != null) {
            return get()
                    .flatMap(clientes ->
                        Mono.justOrEmpty(clientes.stream()
                                .filter(cliente -> cliente.getUsuario().equals(nombre))
                                .findFirst()
                        ));
        } else {
            return Mono.empty();
        }
    }
}