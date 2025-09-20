package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioObra {
    List<Obra> obtenerTodas();
    List<Obra> obtenerPorAutor(String autor);
    Obra obtenerPorId(Long id);
}