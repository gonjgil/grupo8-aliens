package com.tallerwebi.dominio.excepcion;

public class StockInsuficienteException extends Exception {

    public StockInsuficienteException(){
        super("Stock insuficiente");
    }
}
