package com.tallerwebi.dominio.excepcion;

public class CarritoVacioException extends Exception {
    public CarritoVacioException() {
        super("El carrito está vacío");
    }
}