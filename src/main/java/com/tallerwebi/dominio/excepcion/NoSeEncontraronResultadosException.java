package com.tallerwebi.dominio.excepcion;

public class NoSeEncontraronResultadosException extends RuntimeException {
    public NoSeEncontraronResultadosException(String message) {
        super(message);
    }
}
