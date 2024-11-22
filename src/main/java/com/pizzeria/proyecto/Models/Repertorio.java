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
}