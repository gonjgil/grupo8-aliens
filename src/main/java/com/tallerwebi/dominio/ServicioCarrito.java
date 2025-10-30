package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import com.tallerwebi.presentacion.ItemCarritoDto;

import java.util.List;

public interface ServicioCarrito {
    Carrito obtenerOCrearCarritoParaUsuario(Usuario usuario); //testeado
    boolean agregarObraAlCarrito(Usuario usuario, Long obraId) throws NoExisteLaObra, NoHayStockSuficiente; // testeado
    void disminuirCantidadDeObraDelCarrito(Usuario usuario, Long obraId); // testeado
    void eliminarObraDelCarrito(Usuario usuario, Long obraId);
    void vaciarCarrito(Usuario usuario); //test
    List<Obra> obtenerObras(Usuario usuario); //testeado
    List<ItemCarritoDto> obtenerItems(Usuario usuario);
    Carrito obtenerCarritoConItems(Usuario usuario) throws CarritoVacioException, CarritoNoEncontradoException; //testeado
    Double calcularPrecioTotalCarrito(Usuario usuario); //testeado
    Integer contarItemsEnCarrito(Usuario usuario); //testeado
}