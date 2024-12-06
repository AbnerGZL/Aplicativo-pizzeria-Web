package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class DetallePedido {
    private Integer id_detalle;
    private Double precio;
    private Integer id_pedido;
    private Integer id_proventa;

    public DetallePedido(Integer id_detalle, Double precio, Integer id_pedido, Integer id_proventa) {
        this.id_detalle = id_detalle;
        this.precio = precio;
        this.id_pedido = id_pedido;
        this.id_proventa = id_proventa;
    }
}
