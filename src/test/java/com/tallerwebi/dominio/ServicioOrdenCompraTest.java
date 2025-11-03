package com.tallerwebi.dominio;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.enums.EstadoOrdenCompra;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioOrdenCompraTest {

    private RepositorioOrdenCompra repositorioOrdenCompra;
    private RepositorioCarrito repositorioCarrito;
    private PaymentClient mpClient;
    private Payment pagoMP;
    private ServicioOrdenCompraImpl servicioOrdenCompra;

    @BeforeEach
    public void init() {
        this.repositorioOrdenCompra = mock(RepositorioOrdenCompra.class);
        this.repositorioCarrito = mock(RepositorioCarrito.class);
        this.mpClient = mock(PaymentClient.class);
        this.pagoMP = mock(Payment.class);
        this.servicioOrdenCompra = new ServicioOrdenCompraImpl(repositorioOrdenCompra, repositorioCarrito,  mpClient);
    }


    @Test
    public void deberiaCrearOrdenDeCompraAPartirDeCarritoCorrectamente() throws CarritoVacioException {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);

        Obra obra1 = new Obra();
        Obra obra2 = new Obra();

        obra1.setPrecio(100.0);
        obra1.setId(1L);
        carrito.agregarItem(obra1);
        obra2.setPrecio(100.0);
        obra2.setId(1L);
        carrito.agregarItem(obra2);
        carrito.setId(1L);
        carrito.setEstado(EstadoCarrito.FINALIZADO);

        when(repositorioCarrito.obtenerPorId(1L)).thenReturn(carrito);

        OrdenCompra ordenCreada = this.servicioOrdenCompra.crearOrdenDeCompraAPartirDeCarrito(carrito);

        assertThat(ordenCreada.getUsuario(), is(carrito.getUsuario()));
        assertThat(ordenCreada.getPrecioFinal(), is(carrito.getTotal()));
        assertThat(ordenCreada.getItems().size(), is(carrito.getItems().size()));
    }

    @Test
    public void deberiaCrearOrdenDeCompraAPartirDeCarritoCorrectamenteItemCarritoDebeConvertirseEnItemOrdenCorrectamente() throws CarritoVacioException {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);

        Obra obra1 = new Obra();
        Obra obra2 = new Obra();

        obra1.setPrecio(100.0);
        obra1.setId(1L);
        carrito.agregarItem(obra1);
        obra2.setPrecio(100.0);
        obra2.setId(1L);
        carrito.agregarItem(obra2);
        carrito.setId(1L);
        carrito.setEstado(EstadoCarrito.FINALIZADO);

        when(repositorioCarrito.obtenerPorId(1L)).thenReturn(carrito);

        OrdenCompra ordenCreada = this.servicioOrdenCompra.crearOrdenDeCompraAPartirDeCarrito(carrito);

        assertThat(ordenCreada.getItems().size(), is(carrito.getItems().size()));
        assertThat(ordenCreada.getCarrito().buscarItemPorObra(obra1), is(carrito.buscarItemPorObra(obra1)));
        assertThat(ordenCreada.getCarrito().buscarItemPorObra(obra2), is(carrito.buscarItemPorObra(obra2)));


    }

    @Test
    public void enCasoDeQueElCarritoConElQueQuieroHacerLaOrdenDeCompraEsteSinItemsDebeDarCarritoVacioException() throws CarritoVacioException {
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setEstado(EstadoCarrito.FINALIZADO);
        carrito.setItems(Collections.emptyList());

        when(repositorioCarrito.obtenerPorId(1L)).thenReturn(carrito);

        try {
            this.servicioOrdenCompra.crearOrdenDeCompraAPartirDeCarrito(carrito);
            fail("Se esperaba CarritoVacioException pero no fue lanzada");
        } catch (CarritoVacioException e) {

        }

    }

    @Test
    public void enCasoDeQueElPagoSeaRechazadoElEstadoDeLaOrdenDeCompraDebeSerRECHAZADA() throws MPException, MPApiException {
        OrdenCompra orden = new OrdenCompra();
        orden.setId(1L);
        orden.setPagoId(1L);

        when(repositorioOrdenCompra.obtenerPorId(1L)).thenReturn(orden);
        when(mpClient.get(1L)).thenReturn(pagoMP);
        when(pagoMP.getStatus()).thenReturn("rejected");

        EstadoOrdenCompra estado = servicioOrdenCompra.obtenerEstadoDeOrden(1L);

        assertEquals(EstadoOrdenCompra.RECHAZADA, estado);
    }

    @Test
    public void deberiaObtenerOrdenesDeCompraPorEstadoDeOrdenCorrectamente(){
        OrdenCompra orden1 = new OrdenCompra();
        orden1.setId(1L);
        orden1.setPagoId(1L);
        orden1.setEstado(EstadoOrdenCompra.RECHAZADA);

        OrdenCompra orden2 = new OrdenCompra();
        orden2.setId(2L);
        orden2.setPagoId(2L);
        orden2.setEstado(EstadoOrdenCompra.RECHAZADA);

        OrdenCompra orden3 = new OrdenCompra();
        orden3.setId(3L);
        orden3.setPagoId(3L);
        orden3.setEstado(EstadoOrdenCompra.APROBADA);


        when(repositorioOrdenCompra.obtenerPorEstado(EstadoOrdenCompra.RECHAZADA)).thenReturn(List.of(orden1, orden2));


        List<OrdenCompra> ordenes = servicioOrdenCompra.obtenerOrdenesDeCompraPorEstadoDeOrden(EstadoOrdenCompra.RECHAZADA);

        assertThat(ordenes.size(), is(2));
        assertThat(ordenes, contains(orden1, orden2));
        assertThat(ordenes, not(hasItem(orden3)));

    }


}
