package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Paquete {
    private String id_paquete;
    private String id_proventa;
    private String id_proprima;
    private String cantidad;

    public Paquete(String id_paquete, String id_proventa, String id_proprima, String cantidad) {
        this.id_paquete = id_paquete;
        this.id_proventa = id_proventa;
        this.id_proprima = id_proprima;
        this.cantidad = cantidad;
    }
}
