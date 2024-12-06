package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Pedido {
    private Integer id_pedido;
    private Integer id_sucursal;
    private Integer id_cliente;
    private String fecha_pedido;
    private String fecha_entrega;
    private String estado;
    private String codigo;
    private String direccion;


    public Pedido(Integer id_pedido, Integer id_sucursal, Integer id_cliente, String fecha_pedido, String fecha_entrega, String estado, String codigo, String direccion) {
        this.id_pedido = id_pedido;
        this.id_sucursal = id_sucursal;
        this.id_cliente = id_cliente;
        this.fecha_pedido = fecha_pedido;
        this.fecha_entrega = fecha_entrega;
        this.estado = estado;
        this.codigo = codigo;
        this.direccion = direccion;
    }
}