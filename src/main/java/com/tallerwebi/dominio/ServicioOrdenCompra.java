package com.tallerwebi.dominio;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.OrdenCompra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.EstadoOrdenCompra;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.presentacion.dto.ItemCarritoDto;
import com.tallerwebi.presentacion.dto.ItemOrdenDto;

import java.util.List;
import java.util.Map;

public interface ServicioOrdenCompra {

    OrdenCompra crearOrdenDeCompraAPartirDeCarrito(Carrito carrito) throws CarritoVacioException, CarritoNoEncontradoException;
    EstadoOrdenCompra obtenerEstadoDeOrden(Long ordenId) throws MPException, MPApiException;
    List<OrdenCompra> obtenerOrdenesDeCompraPorEstadoDeOrden(EstadoOrdenCompra estado);
    OrdenCompra obtenerOrdenPorId(Long ordenId);
    List<ItemOrdenDto> obtenerItems(Long ordenId);
}
