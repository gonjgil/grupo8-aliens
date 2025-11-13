package com.tallerwebi.dominio.excepcion;

public class NoExisteFormatoObra extends RuntimeException {
    public NoExisteFormatoObra() {
        super("La obra no tiene el formato especificado.");
    }
}
