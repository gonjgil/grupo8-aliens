package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.presentacion.ObraDto;

public interface ServicioGaleria {
    List<ObraDto> obtener();
    List<ObraDto> ordenarRandom();
    List<ObraDto> obtenerPorAutor(String autor);
    List<ObraDto> obtenerPorCategoria(String categoria);
    ObraDto obtenerPorId(Long id);

    void darLike(Long id, Usuario usuario) throws NoExisteLaObra;
}
