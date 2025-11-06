package com.tallerwebi.dominio.enums;

public enum TipoImagen {
    PERFIL_ARTISTA("perfiles_artistas"),
    OBRA("obras");

    private final String carpeta;

    TipoImagen(String carpeta) {
        this.carpeta = carpeta;
    }

    public String getCarpeta() {
        return carpeta;
    }
}
