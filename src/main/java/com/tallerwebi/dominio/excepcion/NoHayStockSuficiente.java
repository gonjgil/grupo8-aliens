package com.tallerwebi.dominio.excepcion;

public class NoHayStockSuficiente extends Exception {

    public NoHayStockSuficiente(String mensaje) {
        super(mensaje);
    }
    public NoHayStockSuficiente() {}
}
