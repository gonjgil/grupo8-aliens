package com.tallerwebi.dominio;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioCompraHecha;
import com.tallerwebi.dominio.servicioImpl.ServicioCompraHechaImpl;
import com.tallerwebi.presentacion.dto.CompraHechaDto;
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

public class ServicioCompraHechaTest {

    private RepositorioCompraHecha repositorioCompraHecha;
    private RepositorioCarrito repositorioCarrito;
    private PaymentClient mpClient;
    private Payment pagoMP;
    private ServicioCompraHechaImpl servicioOrdenCompra;

    @BeforeEach
    public void init() {
        this.repositorioCompraHecha = mock(RepositorioCompraHecha.class);
        this.repositorioCarrito = mock(RepositorioCarrito.class);
        this.mpClient = mock(PaymentClient.class);
        this.pagoMP = mock(Payment.class);
        this.servicioOrdenCompra = new ServicioCompraHechaImpl(repositorioCompraHecha, repositorioCarrito,  mpClient);
    }

    @Test
    public void deberiaCrearCompraHechaAPartirDeCarritoCorrectamente() throws CarritoVacioException, CarritoNoEncontradoException {
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

        CompraHecha ordenCreada = this.servicioOrdenCompra.crearCompraHechaAPartirDeCarrito(carrito);

        assertThat(ordenCreada.getUsuario(), is(carrito.getUsuario()));
        assertThat(ordenCreada.getPrecioFinal(), is(carrito.getTotal()));
        assertThat(ordenCreada.getItems().size(), is(carrito.getItems().size()));
    }

    @Test
    public void deberiaCrearOrdenDeCompraAPartirDeCarritoCorrectamenteItemCarritoDebeConvertirseEnItemOrdenCorrectamenteHecha() throws CarritoVacioException, CarritoNoEncontradoException {
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

        CompraHecha ordenCreada = this.servicioOrdenCompra.crearCompraHechaAPartirDeCarrito(carrito);

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
            this.servicioOrdenCompra.crearCompraHechaAPartirDeCarrito(carrito);
            fail("Se esperaba CarritoVacioException pero no fue lanzada");
        } catch (CarritoVacioException | CarritoNoEncontradoException e) {

        }

    }

    @Test
    public void deberiaObtenerPorIdCorrectamente() throws CarritoNoEncontradoException, CarritoVacioException {
        Usuario usuario = new Usuario();
        usuario.setId(10L);

        CompraHecha compra = new CompraHecha();
        compra.setId(1L);
        compra.setUsuario(usuario);
        compra.setPrecioFinal(200.0);

        when(repositorioCompraHecha.obtenerPorId(1L)).thenReturn(compra);

        CompraHecha resultado = servicioOrdenCompra.obtenerCompraPorId(1L);

        assertThat(resultado, is(compra));

    }



}
