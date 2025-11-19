package com.tallerwebi.dominio.servicioImpl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.enums.EstadoPago;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.PagoNoAprobadoException;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioCompraHecha;
import com.tallerwebi.presentacion.dto.ItemCompraDto;
import com.tallerwebi.presentacion.dto.CompraHechaDto;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("ServicioCompraHecha")
@Transactional
public class ServicioCompraHechaImpl implements ServicioCompraHecha {


    private RepositorioCompraHecha repositorioCompraHecha;
    private RepositorioCarrito repositorioCarrito;
    private ServicioCarrito servicioCarrito;
    private ServicioPago servicioPago;
    private ServicioMail servicioMail;

    @Autowired
    public ServicioCompraHechaImpl(RepositorioCompraHecha repositorioOrden, RepositorioCarrito repositorioCarrito, ServicioPago servicioPago, ServicioCarrito servicioCarrito, ServicioMail servicioMail) {
        this.repositorioCompraHecha = repositorioOrden;
        this.repositorioCarrito = repositorioCarrito;
        this.servicioPago = servicioPago;
        this.servicioCarrito = servicioCarrito;
        this.servicioMail = servicioMail;
    }

    @Override
    public CompraHecha crearResumenCompraAPartirDeCarrito(Carrito carrito, Long pagoId) throws CarritoVacioException, CarritoNoEncontradoException, PagoNoAprobadoException {
        CompraHecha resumenCreado;
        Pago resultadoPago = servicioPago.consultarEstadoDePago(pagoId);

        if (!resultadoPago.getExitoso() || resultadoPago.getEstado() != EstadoPago.APROBADO) {
            throw new PagoNoAprobadoException("El pago no fue aprobado");
        }

        this.validarCarrito(carrito);

        if (repositorioCarrito.obtenerPorId(carrito.getId()) != null && carrito.getEstado() == EstadoCarrito.ACTIVO) {
            List<ItemCompra> itemsConvertidos = this.convertirItemCarritoAItemOrden(carrito.getItems());

            resumenCreado =  new CompraHecha(itemsConvertidos, carrito, carrito.getTotal(), resultadoPago.getIdTransaccion(), resultadoPago.getEstado(), carrito.getUsuario());
            for (ItemCompra item : itemsConvertidos) {
                item.setCompra(resumenCreado);
            }

            CompraHecha guardada = repositorioCompraHecha.guardar(resumenCreado);

            servicioMail.enviarMailConfirmacionCompra(carrito.getUsuario(), guardada, itemsConvertidos);

            servicioCarrito.finalizarCompra(carrito.getUsuario());

            return  guardada;
        }

        throw new IllegalStateException("No se pudo crear la compra: el carrito no está finalizado o no existe.");
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
        CompraHecha compra = repositorioCompraHecha.obtenerPorId(compraId);
        if (compra != null) {
            Hibernate.initialize(compra.getItems());
        }
        return compra;
    }


    @Override
    public List<CompraHecha> obtenerComprasPorUsuario(Usuario usuario) {
        List<CompraHecha> compras = repositorioCompraHecha.obtenerTodasPorUsuario(usuario);
        if(compras == null){
            return new ArrayList<>();
        }
        return compras;
    }

    @Override
    public List<ItemCompra> obtenerItems(Long ordenId) {
        CompraHecha compra = repositorioCompraHecha.obtenerPorId(ordenId);
        if(compra == null){
            return new ArrayList<>();
        }
        return compra.getItems();
    }


}
