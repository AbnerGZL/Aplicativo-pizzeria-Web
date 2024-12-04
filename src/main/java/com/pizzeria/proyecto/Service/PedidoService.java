package com.pizzeria.proyecto.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.pizzeria.proyecto.Models.Paquete;
import com.pizzeria.proyecto.Models.Pedido;
import com.pizzeria.proyecto.Models.ProductoPrima;
import lombok.Lombok;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class PedidoService {
    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    public PedidoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<Pedido>> obtenerPedidosPorEstado(String filtro, Integer idCliente) {
        Mono<List<Pedido>> pedidoMono = null;
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_DATE_TIME;
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            pedidoMono = webClient.get()
                    .uri("/pedidos")
                    .retrieve()
                    .bodyToMono(Pedido[].class)
                    .map(Arrays::asList)
                    .map(pedidos -> pedidos.stream()
                            .filter(pedido -> pedido.getEstado().equalsIgnoreCase(filtro))
                            .filter(pedido -> pedido.getId_cliente().equals(idCliente))
                            .map(pedido -> {
                                pedido.setFecha_pedido(formatDate(pedido.getFecha_pedido(), inputFormatter, outputFormatter));
                                pedido.setFecha_entrega(formatDate(pedido.getFecha_entrega(), inputFormatter, outputFormatter));
                                return pedido;
                            })
                            .collect(Collectors.toList()));

            return pedidoMono;

        } catch (Exception e) {
            log.error("Error al obtener los pedidos: ", e);
            return Mono.error(e);
        }
    }

    public Mono<List<String>> obtenerProductosDePedido(Integer idPedido) {
        return obtenerIdProventaDeDetallePedido(idPedido)
                .flatMap(this::obtenerProductosPedido);
    }

    private Mono<Integer> obtenerIdProventaDeDetallePedido(Integer idPedido) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/detalles-pedido")
                        .queryParam("id_pedido", idPedido)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMap(response -> {
                    if (response.isArray()) {
                        for (JsonNode detalle : response) {
                            if (detalle.get("id_pedido").asInt() == idPedido) {
                                return Mono.justOrEmpty(detalle.get("id_proventa").asInt());
                            }
                        }
                    }
                    return Mono.empty();
                })
                .onErrorResume(e -> {
                    log.error("Error al obtener id_proventa de detalle pedido", e);
                    return Mono.empty();
                });
    }

    public Mono<List<String>> obtenerProductosPedido(Integer idProventa) {
        return webClient
                .get()
                .uri("/paquetes/id_proventa/{id}", idProventa)
                .retrieve()
                .bodyToMono(Object.class)
                .flatMap(paquetesResponse -> {
                    List<Paquete> paquetes = parsePaquetesResponse(paquetesResponse);
                    List<Mono<String>> nombresMonos = new ArrayList<>();

                    for (Paquete paquete : paquetes) {
                        nombresMonos.add(
                                webClient
                                        .get()
                                        .uri("/productos-prima/id_proprima/{id}", paquete.getId_proprima())
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<List<ProductoPrima>>() {})
                                        .map(productos -> {
                                            ProductoPrima productoPrima = productos.get(0);
                                            return productoPrima.getNombre() + " x " + paquete.getCantidad();
                                        })
                        );
                    }

                    return Mono.zip(nombresMonos, results -> {
                        List<String> nombres = new ArrayList<>();
                        for (Object result : results) {
                            nombres.add((String) result);
                        }
                        return nombres;
                    });
                });
    }

    private List<Paquete> parsePaquetesResponse(Object paquetesResponse) {
        if (paquetesResponse instanceof List) {
            List<?> rawList = (List<?>) paquetesResponse;
            List<Paquete> paquetes = new ArrayList<>();
            for (Object item : rawList) {
                if (item instanceof LinkedHashMap) {
                    paquetes.add(mapToPaquete((LinkedHashMap<String, Object>) item));
                }
            }
            return paquetes;
        } else if (paquetesResponse instanceof LinkedHashMap) {
            return List.of(mapToPaquete((LinkedHashMap<String, Object>) paquetesResponse));
        } else {
            throw new IllegalArgumentException("Tipo de respuesta no soportado");
        }
    }

    private Paquete mapToPaquete(LinkedHashMap<String, Object> map) {
        Paquete paquete = new Paquete();
        paquete.setId_paquete((Integer) map.get("id_paquete"));
        paquete.setCantidad((Integer) map.get("cantidad"));
        paquete.setId_proventa((Integer) map.get("id_proventa"));
        paquete.setId_proprima((Integer) map.get("id_proprima"));
        return paquete;
    }

    public Mono<Double> obtenerPrecioPedido(Integer idPedido) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/pagos").build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMapMany(response -> Flux.fromIterable(response))
                .filter(pago -> pago.get("id_pedido").asInt() == idPedido)
                .map(pago -> pago.get("monto").asDouble())
                .next()
                .defaultIfEmpty(0.0);
    }

    private String formatDate(String date, DateTimeFormatter inputFormatter, DateTimeFormatter outputFormatter) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(date, inputFormatter);
            return localDateTime.format(outputFormatter);
        } catch (Exception e) {
            log.error("Error al formatear la fecha: ", e);
            return date;
        }
    }
}


