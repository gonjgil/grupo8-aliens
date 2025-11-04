package com.tallerwebi.dominio.enums;

public enum Formato {

        ORIGINAL("Original"),
        ORIGINAL_FIRMADO("Original Firmado"),
        DIGITAL("Digital"),
        DIGITAL_NFT("Digital NFT"),
        IMPRESION_CANVAS("Impresión Canvas"),
        IMPRESION_PREMIUM("Impresión Premium");

    private final String formato;

    Formato(String formato) {
        this.formato = formato;
    }

    public String getFormato() {
        return formato;
    }
}
