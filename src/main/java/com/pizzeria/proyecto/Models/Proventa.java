package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Proventa {
    private String id_proventa;
    private String id_repertorio;
    private String fecha_estado;
    private String estado;

    public Proventa(String id_proventa, String id_repertorio, String fecha_estado, String estado) {
        this.id_proventa = id_proventa;
        this.id_repertorio = id_repertorio;
        this.fecha_estado = fecha_estado;
        this.estado = estado;
    }
}
