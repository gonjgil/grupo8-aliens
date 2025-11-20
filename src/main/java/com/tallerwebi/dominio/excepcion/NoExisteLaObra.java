package com.tallerwebi.dominio.excepcion;

public class NoExisteLaObra extends RuntimeException {

    public NoExisteLaObra(String mensaje) {
        super(mensaje);
    }
    public NoExisteLaObra() {}

}
