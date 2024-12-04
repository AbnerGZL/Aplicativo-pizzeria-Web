package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class RepertorioDetalle {
    private String id_detalle_repertorio;
    private Integer id_repertorio;
    private String id_proprima;
    private String producto;
    private Integer unidades;
    private String detalle;

    public RepertorioDetalle(String id_detalle_repertorio, Integer id_repertorio, String id_proprima, String producto, Integer unidades, String detalle) {
        this.id_detalle_repertorio = id_detalle_repertorio;
        this.id_repertorio = id_repertorio;
        this.id_proprima = id_proprima;
        this.producto = producto;
        this.unidades = unidades;
        this.detalle = detalle;
    }
}