package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Proprima {
    private String id_proprima;
    private String nombre;
    private String precio;
    private String tamano;
    private Integer stock;
    private String id_categoria;

    public Proprima(String id_proprima, String nombre, String precio, String tamano, Integer stock, String id_categoria) {
        this.id_proprima = id_proprima;
        this.nombre = nombre;
        this.precio = precio;
        this.tamano = tamano;
        this.stock = stock;
        this.id_categoria = id_categoria;
    }
}
