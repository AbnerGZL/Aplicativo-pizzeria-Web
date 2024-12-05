package com.pizzeria.proyecto.Service;

import com.pizzeria.proyecto.Models.Cliente;
import com.pizzeria.proyecto.Models.Proventa;
import com.pizzeria.proyecto.Repositories.ProventaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProventaService extends ProventaRepository {


    public ProventaService(WebClient webClient) {
        super(webClient);
    }

    public boolean postProventa(Proventa proventa) {
        boolean result = post(proventa);
        if(result) {
            return true;
        }
        return false;
    }

    public Mono<Proventa> getProventaById(Integer id) {
        if (id != null) {
            return get()
                    .flatMap(proventas ->
                            Mono.justOrEmpty(proventas.stream()
                                    .filter(proventa -> proventa.getId_proventa().equals(id))
                                    .findFirst()
                            ));
        } else {
            return Mono.empty();
        }
    }

}
