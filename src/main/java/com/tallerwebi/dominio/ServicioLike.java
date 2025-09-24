package com.tallerwebi.dominio;

public interface ServicioLike {

    boolean darLike(Usuario usuario, Obra obra);
    boolean quitarLike(Usuario usuario, Obra obra);

}
