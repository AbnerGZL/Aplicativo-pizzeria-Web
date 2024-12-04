package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Carrito {

    private String id_carrito;
    private String creacion;
    private String id_cliente;
    private String id_proventa;

    public Carrito(String id_carrito, String creacion, String id_cliente, String id_proventa) {
        this.id_carrito = id_carrito;
        this.creacion = creacion;
        this.id_cliente = id_cliente;
        this.id_proventa = id_proventa;
    }
}
