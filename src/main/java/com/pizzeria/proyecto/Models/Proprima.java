package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Proprima {
    private Integer id_proprima;
    private String nombre;
    private Double precio;
    private String tamano;
    private Integer stock;
    private Integer id_categoria;

    public Proprima(Integer id_proprima, String nombre, Double precio, String tamaño, Integer stock, Integer id_categoria) {
        this.id_proprima = id_proprima;
        this.nombre = nombre;
        this.precio = precio;
        this.tamano = tamaño;
        this.stock = stock;
        this.id_categoria = id_categoria;
    }
}
