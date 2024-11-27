package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Repertorio {
    private int id_repertorio;
    private String titulo;
    private String descripcion;
    private String precio;
    private String fecha_inic;
    private String fecha_fin;
    private String tipo_repertorio;
    private String imagen;

    public Repertorio(int id_repertorio, String titulo, String descripcion, String precio, String fecha_inic, String fecha_fin, String imagen, String tipo_repertorio) {
        this.id_repertorio = id_repertorio;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fecha_inic = fecha_inic;
        this.fecha_fin = fecha_fin;
        this.imagen = imagen;
        this.tipo_repertorio = tipo_repertorio;
    }
}