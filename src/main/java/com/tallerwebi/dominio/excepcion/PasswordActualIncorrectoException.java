package com.tallerwebi.dominio.excepcion;

public class PasswordActualIncorrectoException extends Exception {
    public PasswordActualIncorrectoException(String mensaje) {
        super(mensaje);
    }
}
