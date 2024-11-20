package com.pizzeria.proyecto.Models;

import lombok.Data;

@Data
public class Cliente {
    private String usuario;
    private String correo;
    private String telefono;
    private String contrasena;

    public Cliente(String usuario, String correo, String telefono, String contrasena) {
        this.usuario = usuario;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
    }
}
