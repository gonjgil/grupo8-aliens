package com.tallerwebi.dominio.enums;

public enum Formato {

    ORIGINAL("Original", "Obra original única"),
    ORIGINAL_FIRMADO("Original Firmado", "Obra original con firma del artista"),
    DIGITAL("Digital", "Versión digital de alta calidad"),
    DIGITAL_NFT("Digital NFT", "Token no fungible"),
    IMPRESION_CANVAS("Impresión Canvas", "Impresión sobre lienzo"),
    IMPRESION_PREMIUM("Impresión Premium", "Impresión de lujo");

    private final String formato;
    private final String descripcion;

    Formato(String formato, String descripcion) {
        this.formato = formato;
        this.descripcion = descripcion;
    }

    public String getFormato() {
        return formato;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
