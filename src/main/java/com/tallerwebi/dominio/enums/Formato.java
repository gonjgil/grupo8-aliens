package com.tallerwebi.dominio.enums;

public enum Formato {

        DIGITAL("Digital"),
        FISICO("Físico");

    private final String formato;

    Formato(String formato) {
        this.formato = formato;
    }

    public String getFormato() {
        return formato;
    }
}
