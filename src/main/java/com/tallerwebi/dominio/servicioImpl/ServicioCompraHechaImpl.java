package com.tallerwebi.dominio.servicioImpl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.tallerwebi.dominio.ServicioCompraHecha;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.enums.EstadoPago;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioCompraHecha;
import com.tallerwebi.presentacion.dto.ItemCompraDto;
import com.tallerwebi.presentacion.dto.CompraHechaDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service("ServicioCompraHecha")
@Transactional
public class ServicioCompraHechaImpl implements ServicioCompraHecha {


    private RepositorioCompraHecha repositorioCompraHecha;
    private RepositorioCarrito repositorioCarrito;
    private PaymentClient mpCliente;

    public ServicioCompraHechaImpl(RepositorioCompraHecha repositorioOrden, RepositorioCarrito repositorioCarrito, PaymentClient mpCliente) {
        this.repositorioCompraHecha = repositorioOrden;
        this.repositorioCarrito = repositorioCarrito;
        this.mpCliente = mpCliente;
    }

    @Override
    public CompraHecha crearResumenCompraAPartirDeCarrito(Carrito carrito) throws CarritoVacioException, CarritoNoEncontradoException {
        CompraHecha resumenCreado = new CompraHecha();
        this.validarCarrito(carrito);

        if (repositorioCarrito.obtenerPorId(carrito.getId()) != null && carrito.getEstado() == EstadoCarrito.FINALIZADO) {
            List<ItemCompra> itemsConvertidos = this.convertirItemCarritoAItemOrden(carrito.getItems());

            resumenCreado.setCarrito(carrito);
            resumenCreado.setUsuario(carrito.getUsuario());
            resumenCreado.setItems(itemsConvertidos);
            resumenCreado.setPrecioFinal(carrito.getTotal());

            repositorioCompraHecha.guardar(resumenCreado);
        }

        return resumenCreado;
    }

    private void validarCarrito(Carrito carrito) throws CarritoVacioException, CarritoNoEncontradoException {
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new CarritoVacioException("El carrito no puede estar vacio");
        }
        if(repositorioCarrito.obtenerPorId(carrito.getId()) == null){
            throw new CarritoNoEncontradoException("Carrito no encontrado");
        }
    }

    private List<ItemCompra> convertirItemCarritoAItemOrden(List<ItemCarrito> itemsCarrito) {
        List<ItemCompra> itemsOrden = new ArrayList<>();
        for (ItemCarrito itemCarrito : itemsCarrito) {
            ItemCompra itemCompra = new ItemCompra(itemCarrito);
            itemsOrden.add(itemCompra);
        }
        return itemsOrden;
    }

    @Override
    public CompraHecha obtenerCompraPorId(Long compraId)  {
        return repositorioCompraHecha.obtenerPorId(compraId);
    }


    @Override
    public List<CompraHecha> obtenerComprasPorUsuario(Usuario usuario) {
        List<CompraHecha> compras = repositorioCompraHecha.obtenerTodasPorUsuario(usuario);
        if(compras == null){
            return null;
        }
        return compras;
    }

    @Override
    public List<ItemCompra> obtenerItems(Long ordenId) {
        CompraHecha compra = repositorioCompraHecha.obtenerPorId(ordenId);
        if(compra == null){
            return null;
        }
        return compra.getItems();
    }


}
