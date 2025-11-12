package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.NoExisteFormatoObra;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;

import java.util.List;

public interface ServicioFormatoObra {
    FormatoObra crearFormato(Long obraId, Formato formato, Double precio, Integer stock) throws NoExisteLaObra;
    void eliminarFormato(Long formatoObraId) throws NoExisteFormatoObra;
    void actualizarPrecio(Long formatoObraId, Double nuevoPrecio) throws NoExisteFormatoObra;
    void actualizarStock(Long formatoObraId, Integer nuevoStock) throws NoExisteFormatoObra;
    FormatoObra obtenerPorId(Long formatoObraId) throws NoExisteFormatoObra;
    List<FormatoObra> obtenerFormatosPorObra(Long obraId);
}
