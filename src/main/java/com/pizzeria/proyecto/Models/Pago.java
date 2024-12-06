package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Pago {
    private Integer id_pago;
    private Double monto;
    private String metodo_pago;
    private String estado;
    private Integer id_pedido;

    public Pago(Integer id_pago, Double monto, String metodo_pago, String estado, Integer id_pedido) {
        this.id_pago = id_pago;
        this.monto = monto;
        this.metodo_pago = metodo_pago;
        this.estado = estado;
        this.id_pedido = id_pedido;
    }
}
