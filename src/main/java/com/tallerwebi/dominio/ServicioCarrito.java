package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.presentacion.ItemCarritoDto;
import com.tallerwebi.presentacion.FormatoObraDto;

import java.util.List;

public interface ServicioCarrito {
    Carrito obtenerOCrearCarritoParaUsuario(Usuario usuario);
    boolean agregarObraAlCarrito(Usuario usuario, Long obraId, Formato formato) throws NoExisteLaObra, NoHayStockSuficiente;
    void disminuirCantidadDeObraDelCarrito(Usuario usuario, Long obraId, Formato formato);
    void eliminarObraDelCarrito(Usuario usuario, Long obraId, Formato formato);
    void vaciarCarrito(Usuario usuario);
    List<Obra> obtenerObras(Usuario usuario);
    List<ItemCarritoDto> obtenerItems(Usuario usuario);
    Carrito obtenerCarritoConItems(Usuario usuario);
    Double calcularPrecioTotalCarrito(Usuario usuario);
    Integer contarItemsEnCarrito(Usuario usuario);
    List<FormatoObraDto> obtenerFormatosDisponibles(Long obraId);
}