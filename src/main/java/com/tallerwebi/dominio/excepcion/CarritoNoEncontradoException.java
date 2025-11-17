package com.tallerwebi.dominio.excepcion;

public class CarritoNoEncontradoException extends Exception {
    public CarritoNoEncontradoException(){}

    public CarritoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
