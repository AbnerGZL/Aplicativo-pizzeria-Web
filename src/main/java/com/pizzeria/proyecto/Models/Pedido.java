package com.pizzeria.proyecto.Models;

import lombok.Data;

import java.util.List;

@Data
public class Pedido {
    private String id_pedido;
    private String id_sucursal;
    private Integer id_cliente;
    private String fecha_pedido;
    private String fecha_entrega;
    private String estado;
    private String nombre_ref;
    private String correo;
    private String direccion;
    private List<String> productos;
    private Double precio;

    public Pedido(String id_pedido, String id_sucursal, Integer id_cliente, String fecha_pedido, String fecha_entrega, String estado, String nombre_ref, String correo, String direccion, List<String> productos, Double precio) {
        this.id_pedido = id_pedido;
        this.id_sucursal = id_sucursal;
        this.id_cliente = id_cliente;
        this.fecha_pedido = fecha_pedido;
        this.fecha_entrega = fecha_entrega;
        this.estado = estado;
        this.nombre_ref = nombre_ref;
        this.correo = correo;
        this.direccion = direccion;
        this.productos = productos;
        this.precio = precio;
    }

}
