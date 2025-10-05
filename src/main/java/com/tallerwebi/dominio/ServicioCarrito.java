package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;

public interface ServicioCarrito {
    Carrito obtenerOCrearCarritoParaUsuario(Usuario usuario);
    void agregarObraAlCarrito(Usuario usuario, Long obraId, Integer cantidad) throws NoExisteLaObra;
    void removerObraDelCarrito(Usuario usuario, Long obraId);
    void actualizarCantidadObra(Usuario usuario, Long obraId, Integer nuevaCantidad);
    void limpiarCarrito(Usuario usuario);
    Carrito obtenerCarritoConItems(Usuario usuario);
    Double calcularTotalCarrito(Usuario usuario);
    Integer contarItemsEnCarrito(Usuario usuario);
    void finalizarCarrito(Usuario usuario) throws CarritoVacioException;
}