package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.EstadoCarrito;

public interface RepositorioCarrito {
    void guardar(Carrito carrito);
    Carrito obtenerPorId(Long id);
    Carrito obtenerCarritoActivoPorUsuario(Usuario usuario);
    Carrito crearCarritoParaUsuario(Usuario usuario);
    void eliminar(Carrito carrito);
    void actualizarEstado(Long carritoId, EstadoCarrito estado);
}