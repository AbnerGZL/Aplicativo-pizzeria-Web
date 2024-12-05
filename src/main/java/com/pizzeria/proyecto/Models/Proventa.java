package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Proventa {
    private Integer id_proventa;
    private Integer id_repertorio;
    private String fecha_estado;
    private String estado;

    public Proventa(Integer id_proventa, Integer id_repertorio, String fecha_estado, String estado) {
        this.id_proventa = id_proventa;
        this.id_repertorio = id_repertorio;
        this.fecha_estado = fecha_estado;
        this.estado = estado;
    }
}
