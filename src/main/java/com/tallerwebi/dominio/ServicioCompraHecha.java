package com.tallerwebi.dominio;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.presentacion.dto.ItemCompraDto;
import com.tallerwebi.presentacion.dto.CompraHechaDto;

import java.util.List;

public interface ServicioCompraHecha {

    CompraHecha crearCompraHechaAPartirDeCarrito(Carrito carrito) throws CarritoVacioException, CarritoNoEncontradoException;
    CompraHecha obtenerCompraPorId(Long ordenId) throws MPException, MPApiException;
    List<CompraHechaDto> obtenerComprasPorUsuario(Usuario usuario);
    List<ItemCompraDto> obtenerItems(Long ordenId);
}
