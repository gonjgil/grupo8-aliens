package com.tallerwebi.infraestructura;

import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.ServicioLike;
import com.tallerwebi.dominio.Usuario;

@Service
public class ServicioLikeImpl implements ServicioLike {

    @Override
    public boolean darLike(Usuario usuario, ObraDto obra) {
        if (usuario != null && !obra.getLikes().containsKey(usuario)) {
            obra.getLikes().put(usuario, true);
            return true;
        }
            return false;
    };

    @Override
    public boolean quitarLike(Usuario usuario, ObraDto obra) {
        if (usuario != null && obra.getLikes().containsKey(usuario)){
            obra.getLikes().remove(usuario);
            return true;
        }
        return false;
    }
}
