package com.pizzeria.proyecto.Models;

import lombok.Data;

import java.util.UUID;

@Data
public class Pedido {
    private String id_pedido;
    private String sucursal;
    private String cliente;
    private String fecha_pedido;
    private String fecha_entrega;
    private String codigo;
    private String direccion;

    public Pedido(String id_pedido, String sucursal, String cliente, String fecha_pedido, String fecha_entrega, String direccion) {
        this.id_pedido = id_pedido;
        this.sucursal = sucursal;
        this.cliente = cliente;
        this.fecha_pedido = fecha_pedido;
        this.fecha_entrega = fecha_entrega;
        this.codigo = UUID.randomUUID().toString().substring(0, 15);
        this.direccion = direccion;
    }
}
