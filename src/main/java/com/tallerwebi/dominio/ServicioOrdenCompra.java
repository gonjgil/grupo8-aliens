package com.tallerwebi.dominio;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.enums.EstadoOrdenCompra;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;

import java.util.List;
import java.util.Map;

public interface ServicioOrdenCompra {

    OrdenCompra crearOrdenDeCompraAPartirDeCarrito(Carrito carrito) throws CarritoVacioException;
    EstadoOrdenCompra obtenerEstadoDeOrden(Long ordenId) throws MPException, MPApiException;
    List<OrdenCompra> obtenerOrdenesDeCompraPorEstadoDeOrden(EstadoOrdenCompra estado);

}
