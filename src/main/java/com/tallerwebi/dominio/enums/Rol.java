package com.tallerwebi.dominio.enums;

public enum Rol {

    ADMIN("Administrador"),
    USUARIO("Usuario"),
    ARTISTA("Artista");

    private final String rol;

    Rol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }
}
