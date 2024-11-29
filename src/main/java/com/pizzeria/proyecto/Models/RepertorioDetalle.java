package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class RepertorioDetalle {
    private String id_detalle_repertorio;
    private String id_repertorio;
    private String producto;
    private Integer unidades;
    private String detalle;

    public RepertorioDetalle(String id_detalle_repertorio, String id_repertorio, String producto, Integer unidades, String detalle) {
        this.id_detalle_repertorio = id_detalle_repertorio;
        this.id_repertorio = id_repertorio;
        this.producto = producto;
        this.unidades = unidades;
        this.detalle = detalle;
    }
}
