package com.tallerwebi.dominio;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.enums.EstadoOrdenCompra;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ServicioOrdenCompra")
public class ServicioOrdenCompraImpl implements ServicioOrdenCompra{

    private RepositorioOrdenCompra repositorioOrdenCompra;
    private RepositorioCarrito repositorioCarrito;
    private RepositorioUsuario repositorioUsuario;
    private PaymentClient mpCliente;

    public ServicioOrdenCompraImpl(RepositorioOrdenCompra repositorioOrden,  RepositorioCarrito repositorioCarrito,  PaymentClient mpCliente) {
        this.repositorioOrdenCompra = repositorioOrden;
        this.repositorioCarrito = repositorioCarrito;
        this.mpCliente = mpCliente;
    }

    @Override
    public OrdenCompra crearOrdenDeCompraAPartirDeCarrito(Carrito carrito) throws CarritoVacioException {
        OrdenCompra ordenCreada = new OrdenCompra();

        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new CarritoVacioException("El carrito no puede estar vacio");
        }

        if (repositorioCarrito.obtenerPorId(carrito.getId()) != null && carrito.getEstado() == EstadoCarrito.FINALIZADO) {
            List<ItemOrden> itemsOrden = new ArrayList<>();
            for (ItemCarrito itemCarrito : carrito.getItems()) {
                ItemOrden itemOrden = new ItemOrden(itemCarrito);
                itemsOrden.add(itemOrden);
            }
            ordenCreada.setCarrito(carrito);
            ordenCreada.setUsuario(carrito.getUsuario());
            ordenCreada.setItems(itemsOrden);
            ordenCreada.setPrecioFinal(carrito.getTotal());

            repositorioOrdenCompra.guardar(ordenCreada);
        }


        return ordenCreada;
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


}
