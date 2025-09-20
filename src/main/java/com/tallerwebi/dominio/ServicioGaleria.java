package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.infraestructura.ObraDto;

public interface ServicioGaleria {
    List<ObraDto> obtener();
    Object ordenarRandom();
    List<ObraDto> obtenerPorAutor(String autor);
    List<ObraDto> obtenerPorCategoria(String categoria);
    ObraDto obtenerPorId(Long id);
}
