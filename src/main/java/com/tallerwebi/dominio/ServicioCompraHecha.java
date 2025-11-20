package com.tallerwebi.dominio;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.ItemCompra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import com.tallerwebi.dominio.excepcion.PagoNoAprobadoException;

import java.util.List;

public interface ServicioCompraHecha {

    CompraHecha crearResumenCompraAPartirDeCarrito(Carrito carrito, Long pagoId) throws CarritoVacioException, CarritoNoEncontradoException, PagoNoAprobadoException;
    CompraHecha crearResumenCompraDirecta(Long obraId, Formato formato, Usuario usuario, Long pagoId) throws PagoNoAprobadoException, NoHayStockSuficiente;
    CompraHecha obtenerCompraPorId(Long ordenId) throws MPException, MPApiException;
    List<CompraHecha> obtenerComprasPorUsuario(Usuario usuario);
    List<ItemCompra> obtenerItems(Long ordenId);
}
