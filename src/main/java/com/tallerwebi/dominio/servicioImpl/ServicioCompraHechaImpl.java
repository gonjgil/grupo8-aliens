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
    public CompraHecha crearCompraHechaAPartirDeCarrito(Carrito carrito) throws CarritoVacioException, CarritoNoEncontradoException {
        CompraHecha ordenCreada = new CompraHecha();
        this.validarCarrito(carrito);

        if (repositorioCarrito.obtenerPorId(carrito.getId()) != null && carrito.getEstado() == EstadoCarrito.FINALIZADO) {
            List<ItemCompra> itemsConvertidos = this.convertirItemCarritoAItemOrden(carrito.getItems());

            ordenCreada.setCarrito(carrito);
            ordenCreada.setUsuario(carrito.getUsuario());
            ordenCreada.setItems(itemsConvertidos);
            ordenCreada.setPrecioFinal(carrito.getTotal());

            repositorioCompraHecha.guardar(ordenCreada);
        }

        return ordenCreada;
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
    public List<CompraHechaDto> obtenerComprasPorUsuario(Usuario usuario) {
        List<CompraHecha> compras = repositorioCompraHecha.obtenerTodasPorUsuario(usuario);
        List<CompraHechaDto> comprasDto = new ArrayList<>();
        for (CompraHecha compra : compras) {
            comprasDto.add(new CompraHechaDto(compra));
        }
        return comprasDto;
    }

    @Override
    public List<ItemCompraDto> obtenerItems(Long ordenId) {
        CompraHecha orden = repositorioCompraHecha.obtenerPorId(ordenId);

        List<ItemCompraDto> itemsEnOrden = new ArrayList<>();
        for (ItemCompra item : orden.getItems()) {
            itemsEnOrden.add(new ItemCompraDto(item));
        }

        return itemsEnOrden;
    }


}
