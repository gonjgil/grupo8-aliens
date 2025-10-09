package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.presentacion.ObraDto;

import java.util.List;

public interface ServicioCarrito {
    Carrito obtenerOCrearCarritoParaUsuario(Usuario usuario);
    boolean agregarObraAlCarrito(Usuario usuario, Long obraId) throws NoExisteLaObra;
    void removerObraDelCarrito(Usuario usuario, Long obraId);
    void actualizarCantidadObra(Usuario usuario, Long obraId, Integer nuevaCantidad);
    void vaciarCarrito(Usuario usuario);
    List<ObraDto> obtenerObras(Usuario usuario);
    Carrito obtenerCarritoConItems(Usuario usuario);
    Double calcularTotalCarrito(Usuario usuario);
    Integer contarItemsEnCarrito(Usuario usuario);
    void finalizarCarrito(Usuario usuario) throws CarritoVacioException;

    Integer getCantidadTotal();

}