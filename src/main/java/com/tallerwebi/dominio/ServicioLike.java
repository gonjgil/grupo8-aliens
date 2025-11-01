package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;

import java.util.List;

public interface ServicioLike {

    boolean toggleLike(Usuario usuario, Long obraId); //testeado
    Integer contarLikes(Long obraId); //sin implementar
    List<Obra> obtenerObrasLikeadasPorUsuario(Usuario usuario); //sin implementar
}
