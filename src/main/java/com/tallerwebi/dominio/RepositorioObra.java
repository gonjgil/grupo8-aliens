package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioObra {
    List<Obra> obtenerTodas();
    List<Obra> obtenerPorAutor(String autor);
    List<Obra> obtenerPorCategoria(String categoria);
    Obra obtenerPorId(Long id);
    void darLike(Obra obra, Usuario usuario);
    void quitarLike(Obra obra, Usuario usuario);
}