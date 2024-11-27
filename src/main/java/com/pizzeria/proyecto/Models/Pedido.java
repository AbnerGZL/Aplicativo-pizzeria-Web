package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Pedido {
    private String id_pedido;
    private String sucursal;
    private String cliente;
    private String fecha_pedido;
    private String fecha_entrega;
    private String nombre_ref;
    private String correo;
    private String direccion;

    public Pedido(String id_pedido, String sucursal, String cliente, String fecha_pedido, String fecha_entrega, String nombre_ref, String correo, String direccion) {
        this.id_pedido = id_pedido;
        this.sucursal = sucursal;
        this.cliente = cliente;
        this.fecha_pedido = fecha_pedido;
        this.fecha_entrega = fecha_entrega;
        this.nombre_ref = nombre_ref;
        this.correo = correo;
        this.direccion = direccion;
    }
}
