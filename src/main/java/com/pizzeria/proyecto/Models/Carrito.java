package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Carrito {

    private Integer id_carrito;
    private String creacion;
    private Integer id_cliente;
    private Integer id_proventa;

    public Carrito(Integer id_carrito, String creacion, Integer id_cliente, Integer id_proventa) {
        this.id_carrito = id_carrito;
        this.creacion = creacion;
        this.id_cliente = id_cliente;
        this.id_proventa = id_proventa;
    }
}
