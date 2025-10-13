package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioLike")
public class ServicioLikeImpl implements ServicioLike {

    @Override
    @Transactional
    public boolean darLike(Usuario usuario, Obra obra) {
        if (usuario != null && !obra.getUsuariosQueDieronLike().contains(usuario)) {
            obra.darLike(usuario);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean quitarLike(Usuario usuario, Obra obra) {
        if (usuario != null && obra.getUsuariosQueDieronLike().contains(usuario)) {
            obra.quitarLike(usuario);
            return true;
        }
        return false;
    }
}
