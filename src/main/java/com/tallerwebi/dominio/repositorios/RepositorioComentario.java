package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.Comentario;

import java.util.List;

public interface RepositorioComentario {
    void guardar(Comentario comentario);
    List<Comentario> obtenerPorObra(Long obraId);
}
