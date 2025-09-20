package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.infraestructura.ObraDto;

public interface ServicioGaleria {
    List<ObraDto> obtener();
    List<ObraDto> obtenerPorAutor(String autor);
    ObraDto obtenerPorId(Long id);
}
