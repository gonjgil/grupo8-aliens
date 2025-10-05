package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioObra {
    void guardar(Obra obra);
    List<Obra> obtenerTodas();
    List<Obra> obtenerPorAutor(String autor);
    List<Obra> obtenerPorCategoria(String categoria);
    Obra obtenerPorId(Long id);
}