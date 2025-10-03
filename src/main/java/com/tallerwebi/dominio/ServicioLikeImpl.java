package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioLike")
public class ServicioLikeImpl implements ServicioLike {

    @Override
    public boolean darLike(Usuario usuario, Obra obra) {
        if (usuario != null && !obra.getUsuariosQueDieronLike().contains(usuario)) {
            obra.darLike(usuario);
            return true;
        }
        return false;
    }

    @Override
    public boolean quitarLike(Usuario usuario, Obra obra) {
        if (usuario != null && obra.getUsuariosQueDieronLike().contains(usuario)) {
            obra.quitarLike(usuario);
            return true;
        }
        return false;
    }
}
