package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.enums.Categoria;

public interface RepositorioObra {
    void guardar(Obra obra);
    List<Obra> obtenerTodas();
    List<Obra> obtenerPorAutor(String autor);
    List<Obra> obtenerPorCategoria(Categoria categoria);
    Obra obtenerPorId(Long id);
    void darLike(Long id, Usuario usuario);
    void quitarLike(Long id, Usuario usuario);
}