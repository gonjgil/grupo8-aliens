package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.presentacion.ObraDto;

import java.util.List;
import java.util.Set;

public interface ServicioCarrito {
    Carrito obtenerOCrearCarritoParaUsuario(Usuario usuario);
    boolean agregarObraAlCarrito(Usuario usuario, Long obraId) throws NoExisteLaObra;
    void eliminarObraDelCarrito(Usuario usuario, Long obraId);
    void vaciarCarrito(Usuario usuario);
    List<ObraDto> obtenerObras(Usuario usuario);
    Carrito obtenerCarritoConItems(Usuario usuario);
    Double calcularPrecioTotalCarrito(Usuario usuario);
    Integer contarItemsEnCarrito(Usuario usuario);
    void finalizarCarrito(Usuario usuario) throws CarritoVacioException;

    Integer getCantidadTotal();

    /////////////////////////////////////////////////////////
    void agregar(Obra obra);
    void eliminar(Long idObra);
    void vaciar();
    Set<Obra> obtenerItems();
}