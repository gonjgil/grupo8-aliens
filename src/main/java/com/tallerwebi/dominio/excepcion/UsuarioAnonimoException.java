package com.tallerwebi.dominio.excepcion;

public class UsuarioAnonimoException extends RuntimeException {

    public UsuarioAnonimoException() {
        super("Inicia sesion para realizar esta accion");
    }
}
