package com.tallerwebi.dominio.servicioImpl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioCompraHecha;
import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.enums.EstadoPago;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioCompraHecha;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.infraestructura.RepositorioObraImpl;
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


    private RepositorioObra repositorioObra;
    private RepositorioCompraHecha repositorioCompraHecha;
    private RepositorioCarrito repositorioCarrito;
    private ServicioCarrito servicioCarrito;
    private ServicioPago servicioPago;

    @Autowired
    public ServicioCompraHechaImpl(RepositorioCompraHecha repositorioOrden, RepositorioCarrito repositorioCarrito, ServicioPago servicioPago, ServicioCarrito servicioCarrito, RepositorioObra repositorioObra) {
        this.repositorioCompraHecha = repositorioOrden;
        this.repositorioCarrito = repositorioCarrito;
        this.servicioPago = servicioPago;
        this.servicioCarrito = servicioCarrito;
        this.repositorioObra = repositorioObra;
    }

    @Override
    public CompraHecha crearResumenCompraAPartirDeCarrito(Carrito carrito, Long pagoId) throws CarritoVacioException, CarritoNoEncontradoException, PagoNoAprobadoException {
        CompraHecha resumenCreado;
        Pago resultadoPago = servicioPago.consultarEstadoDePago(pagoId);

        if (!resultadoPago.getExitoso() || resultadoPago.getEstado() != EstadoPago.APROBADO) {
            throw new PagoNoAprobadoException("El pago no fue aprobado");
        }

        this.validarCarrito(carrito);

        if (carrito.getEstado() == EstadoCarrito.ACTIVO) {
            List<ItemCompra> itemsConvertidos = this.convertirItemCarritoAItemOrden(carrito.getItems());

            resumenCreado =  new CompraHecha(itemsConvertidos, carrito, carrito.getTotal(), resultadoPago.getIdTransaccion(), resultadoPago.getEstado(), carrito.getUsuario());
            for (ItemCompra item : itemsConvertidos) {
                item.setCompra(resumenCreado);
            }

            CompraHecha guardada = repositorioCompraHecha.guardar(resumenCreado);

            servicioCarrito.finalizarCompra(carrito.getUsuario());
            return  guardada;
        }

        throw new IllegalStateException("No se pudo crear la compra: el carrito no está activo o no existe.");
    }

    @Override
    public CompraHecha crearResumenCompraDirecta(Long obraId, Formato formato, Usuario usuario, Long pagoId) throws PagoNoAprobadoException, NoHayStockSuficiente {

        Pago resultadoPago = servicioPago.consultarEstadoDePago(pagoId);
        Obra obra = repositorioObra.obtenerPorId(obraId);
        int cantidad = 1;

        if(obra == null){
            throw new NoExisteLaObra("La obra no existe");
        }

        if (!resultadoPago.getExitoso() || resultadoPago.getEstado() != EstadoPago.APROBADO) {
            throw new PagoNoAprobadoException("El pago no fue aprobado");
        }

        ItemCompra itemCompra = new ItemCompra();
        itemCompra.setObra(obra);
        itemCompra.setFormato(formato);
        itemCompra.setCantidad(cantidad);
        for(FormatoObra formatoObra : obra.getFormatos()) {
            if (formatoObra.getFormato() == formato) {
                itemCompra.setPrecioUnitario(formatoObra.getPrecio());
                break;
            }
        }
        List<ItemCompra> items = new ArrayList<>();
        items.add(itemCompra);

        CompraHecha resumenCreado =  new CompraHecha(items, null, itemCompra.getSubtotal(), resultadoPago.getIdTransaccion(), resultadoPago.getEstado(), usuario);
        itemCompra.setCompra(resumenCreado);


        return repositorioCompraHecha.guardar(resumenCreado);
    }

    private void validarCarrito(Carrito carrito) throws CarritoVacioException, CarritoNoEncontradoException {
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new CarritoVacioException("El carrito no puede estar vacio");
        }
        if(repositorioCarrito.obtenerPorId(carrito.getId()) == null){
            throw new CarritoNoEncontradoException("Carrito no encontrado");
        }
    }

    public List<ItemCompra> convertirItemCarritoAItemOrden(List<ItemCarrito> itemsCarrito) {
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
