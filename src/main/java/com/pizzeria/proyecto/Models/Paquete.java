package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Paquete {
    private Integer id_paquete;
    private Integer id_proventa;
    private Integer id_proprima;
    private Integer cantidad;

    public Paquete(Integer id_paquete, Integer id_proventa, Integer id_proprima, Integer cantidad) {
        this.id_paquete = id_paquete;
        this.id_proventa = id_proventa;
        this.id_proprima = id_proprima;
        this.cantidad = cantidad;
    }

}
