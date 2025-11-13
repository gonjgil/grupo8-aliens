package com.tallerwebi.dominio;

public interface ServicioPassword {
    String hashearPassword(String password);
    boolean verificarPassword(String passwordIngresado, String hashGuardado);
}
