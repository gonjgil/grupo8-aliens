package com.tallerwebi.dominio.excepcion;

public class CarritoVacioException extends Exception {

    public CarritoVacioException(){}

    public CarritoVacioException(String mensaje) {
        super(mensaje);
    }

}