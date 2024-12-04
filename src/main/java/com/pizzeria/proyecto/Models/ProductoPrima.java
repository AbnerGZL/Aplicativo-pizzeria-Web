package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class ProductoPrima {
    private Integer id_proprima;
    private String nombre;
    private Double precio;
    private String descripcion;
    private Integer stock;
    private Integer id_categoria;
}
