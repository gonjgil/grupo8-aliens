package com.tallerwebi.dominio.enums;

public enum Categoria {

    PINTURA("Pintura"),
    ESCULTURA("Escultura"),
    FOTOGRAFIA("Fotografía"),
    DIBUJO("Dibujo"),
    ARTE_DIGITAL("Arte Digital"),
    ARTE_CERAMICO("Arte Cerámico"),
    ARTE_TEXTIL("Arte Textil"),
    ARTE_MIXTO("Arte Mixto"),
    OTRO("Otro"),
    ABSTRACTO("Abstracto"),
    MODERNO("Moderno"),
    COSMICO("Cosmico"),
    RETRATO("Retrato"),
    SURREALISMO("Surrealismo"),
    TEST("Solo usada para testing");

    private final String categoria;

    Categoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

}
