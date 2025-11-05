package com.tallerwebi.dominio.servicioImpl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.tallerwebi.dominio.ServicioOrdenCompra;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.enums.EstadoOrdenCompra;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioOrdenCompra;
import com.tallerwebi.presentacion.dto.ItemOrdenDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service("ServicioOrdenCompra")
@Transactional
public class ServicioOrdenCompraImpl implements ServicioOrdenCompra {

    private RepositorioOrdenCompra repositorioOrdenCompra;
    private RepositorioCarrito repositorioCarrito;
    private PaymentClient mpCliente;

    public ServicioOrdenCompraImpl(RepositorioOrdenCompra repositorioOrden,  RepositorioCarrito repositorioCarrito,  PaymentClient mpCliente) {
        this.repositorioOrdenCompra = repositorioOrden;
        this.repositorioCarrito = repositorioCarrito;
        this.mpCliente = mpCliente;
    }

    @Override
    public OrdenCompra crearOrdenDeCompraAPartirDeCarrito(Carrito carrito) throws CarritoVacioException, CarritoNoEncontradoException {
        OrdenCompra ordenCreada = new OrdenCompra();
        this.validarCarrito(carrito);

        if (repositorioCarrito.obtenerPorId(carrito.getId()) != null && carrito.getEstado() == EstadoCarrito.FINALIZADO) {
            List<ItemOrden> itemsConvertidos = this.convertirItemCarritoAItemOrden(carrito.getItems());

            ordenCreada.setCarrito(carrito);
            ordenCreada.setUsuario(carrito.getUsuario());
            ordenCreada.setItems(itemsConvertidos);
            ordenCreada.setPrecioFinal(carrito.getTotal());

            repositorioOrdenCompra.guardar(ordenCreada);
        }

        return ordenCreada;
    }

    public void validarCarrito(Carrito carrito) throws CarritoVacioException, CarritoNoEncontradoException {
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new CarritoVacioException("El carrito no puede estar vacio");
        }
        if(repositorioCarrito.obtenerPorId(carrito.getId()) == null){
            throw new CarritoNoEncontradoException("Carrito no encontrado");
        }
    }

    public List<ItemOrden> convertirItemCarritoAItemOrden(List<ItemCarrito> itemsCarrito) {
        List<ItemOrden> itemsOrden = new ArrayList<>();
        for (ItemCarrito itemCarrito : itemsCarrito) {
            ItemOrden itemOrden = new ItemOrden(itemCarrito);
            itemsOrden.add(itemOrden);
        }
        return itemsOrden;
    }

    @Override
    public EstadoOrdenCompra obtenerEstadoDeOrden(Long ordenId) throws MPException, MPApiException {
        OrdenCompra orden = repositorioOrdenCompra.obtenerPorId(ordenId);

        if (orden == null || orden.getPagoId() == null) {
            return null;
        }

        try {
            MercadoPagoConfig.setAccessToken(MercadoPagoConfig.getAccessToken());
            Payment pago = mpCliente.get(orden.getPagoId());

            String estadoDelPago = pago.getStatus();
            EstadoOrdenCompra estado;

            switch (estadoDelPago) {
                case "approved":
                    orden.setEstado(EstadoOrdenCompra.APROBADA);
                    break;
                case "rejected":
                    orden.setEstado(EstadoOrdenCompra.RECHAZADA);
                    break;
                default:
                    orden.setEstado(EstadoOrdenCompra.PENDIENTE);

            }
            estado = orden.getEstado();
            repositorioOrdenCompra.actualizarEstado(orden.getId(), estado);

        return estado;

          } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
          }
    }

    @Override
    public List<OrdenCompra> obtenerOrdenesDeCompraPorEstadoDeOrden(EstadoOrdenCompra estado) {
        return repositorioOrdenCompra.obtenerPorEstado(estado);
    }

    @Override
    public OrdenCompra obtenerOrdenPorId(Long ordenId) {
        return repositorioOrdenCompra.obtenerPorId(ordenId);
    }

    @Override
    public List<ItemOrdenDto> obtenerItems(Long ordenId) {
        OrdenCompra orden = repositorioOrdenCompra.obtenerPorId(ordenId);

        List<ItemOrdenDto> itemsEnOrden = new ArrayList<>();
        for (ItemOrden item : orden.getItems()) {
            itemsEnOrden.add(new ItemOrdenDto(item));
        }

        return itemsEnOrden;
    }


}
