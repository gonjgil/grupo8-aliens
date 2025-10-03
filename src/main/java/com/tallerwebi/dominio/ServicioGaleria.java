package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.excepcion.NoExisteLaObra;

public interface ServicioGaleria {
    List<Obra> obtener();
    List<Obra> ordenarRandom();
    List<Obra> obtenerPorAutor(String autor);
    List<Obra> obtenerPorCategoria(String categoria);
    Obra obtenerPorId(Long id);

    void darLike(Long id, Usuario usuario) throws NoExisteLaObra;
}
