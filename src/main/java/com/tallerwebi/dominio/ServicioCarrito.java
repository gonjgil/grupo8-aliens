package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import com.tallerwebi.presentacion.dto.FormatoObraDto;
import com.tallerwebi.presentacion.dto.ItemCarritoDto;

public interface ServicioCarrito {
    Carrito obtenerOCrearCarritoParaUsuario(Usuario usuario);
    boolean agregarObraAlCarrito(Usuario usuario, Long obraId, Formato formato) throws NoExisteLaObra, NoHayStockSuficiente;
    void disminuirCantidadDeObraDelCarrito(Usuario usuario, Long obraId, Formato formato);
    void eliminarObraDelCarrito(Usuario usuario, Long obraId, Formato formato);
    void vaciarCarrito(Usuario usuario);
    List<Obra> obtenerObras(Usuario usuario);
    List<ItemCarritoDto> obtenerItems(Usuario usuario);
    Integer obtenerCantidadDeItemPorId(Usuario usuario, Obra obra);
    Carrito obtenerCarritoConItems(Usuario usuario); //testeado
    Double calcularPrecioTotalCarrito(Usuario usuario); //testeado
    Integer contarItemsEnCarrito(Usuario usuario); //testeado
    List<FormatoObraDto> obtenerFormatosDisponibles(Long obraId);
    void finalizarCompra(Usuario usuario);
}