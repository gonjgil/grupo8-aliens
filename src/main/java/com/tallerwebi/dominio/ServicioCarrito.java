package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ObraDto;

import java.util.List;

public interface ServicioCarrito {
    void agregarObraAlCarrito(Obra obra);
    void eliminarObraDelCarrito(Obra obra);
    void vaciarCarrito();
    Integer getCantidadTotal();
    List<ObraDto> obtenerObras();
}
