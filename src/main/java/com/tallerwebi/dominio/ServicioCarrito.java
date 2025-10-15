package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import com.tallerwebi.presentacion.ObraDto;

import java.util.List;
import java.util.Set;

public interface ServicioCarrito {
    Carrito obtenerOCrearCarritoParaUsuario(Usuario usuario); //testeado
    boolean agregarObraAlCarrito(Usuario usuario, Long obraId) throws NoExisteLaObra, NoHayStockSuficiente; // testeado
    void eliminarObraDelCarrito(Usuario usuario, Long obraId); // testeado
    void vaciarCarrito(Usuario usuario); //test
    List<ObraDto> obtenerObras(Usuario usuario); //testeado
    Carrito obtenerCarritoConItems(Usuario usuario); //testeado
    Double calcularPrecioTotalCarrito(Usuario usuario); //testeado
    Integer contarItemsEnCarrito(Usuario usuario); //testeado

    /////////////////////////////////////////////////////////
    void agregar(Obra obra);
    void eliminar(Long idObra);
    void vaciar();
    Set<Obra> obtenerItems();
}