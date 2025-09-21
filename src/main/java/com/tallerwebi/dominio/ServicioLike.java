package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.ObraDto;

public interface ServicioLike {

    boolean darLike(Usuario usuario, ObraDto obra);
    boolean quitarLike(Usuario usuario, ObraDto obra);

}
