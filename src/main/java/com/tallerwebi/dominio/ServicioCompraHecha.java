package com.tallerwebi.dominio;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.ItemCompra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;

import java.util.List;

public interface ServicioCompraHecha {

    CompraHecha crearResumenCompraAPartirDeCarrito(Carrito carrito) throws CarritoVacioException, CarritoNoEncontradoException;
    CompraHecha obtenerCompraPorId(Long ordenId) throws MPException, MPApiException;
    List<CompraHecha> obtenerComprasPorUsuario(Usuario usuario);
    List<ItemCompra> obtenerItems(Long ordenId);
}
