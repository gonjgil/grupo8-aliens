package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.StockInsuficienteException;
import com.tallerwebi.presentacion.ObraDto;

import java.util.List;

public interface ServicioCarrito {
    Carrito obtenerOCrearCarritoParaUsuario(Usuario usuario);
    void agregarObraAlCarrito(Usuario usuario, Long obraId) throws NoExisteLaObra, StockInsuficienteException;
    void removerObraDelCarrito(Usuario usuario, Long obraId);
    void actualizarCantidadObra(Usuario usuario, Long obraId, Integer nuevaCantidad);
    void limpiarCarrito(Usuario usuario);
    List<ObraDto> obtenerObras();
    Carrito obtenerCarritoConItems(Usuario usuario);
    Double calcularTotalCarrito(Usuario usuario);
    Integer contarItemsEnCarrito(Usuario usuario);
    void finalizarCarrito(Usuario usuario) throws CarritoVacioException;
}