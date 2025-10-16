package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioLike {

    void toggleLike(Usuario usuario, Long obraId); //testeado
    Integer contarLikes(Long obraId); //sin implementar
    List<Obra> obtenerObrasLikeadasPorUsuario(Usuario usuario); //sin implementar
}
