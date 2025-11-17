package com.tallerwebi.dominio.excepcion;

public class PagoNoAprobadoException extends Exception {

    public PagoNoAprobadoException(String mensaje) {
        super(mensaje);
    }
}
