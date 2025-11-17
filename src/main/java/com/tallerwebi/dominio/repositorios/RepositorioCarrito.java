package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.EstadoCarrito;

public interface RepositorioCarrito {
    Carrito guardar(Carrito carrito);
    Carrito obtenerPorId(Long id);
    Carrito obtenerCarritoActivoPorUsuario(Long usuarioId);
    Carrito obtenerUltimoCarritoPorUsuario(Long usuarioId);
    Carrito crearCarritoParaUsuario(Usuario usuario);
    void eliminar(Carrito carrito);
    void actualizarEstado(Long carritoId, EstadoCarrito estado);
}