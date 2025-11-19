package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.enums.EstadoPago;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.PagoNoAprobadoException;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioCompraHecha;
import com.tallerwebi.dominio.servicioImpl.ServicioCompraHechaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioCompraHechaTest {

    private RepositorioCompraHecha repositorioCompraHecha;
    private RepositorioCarrito repositorioCarrito;
    private ServicioPago servicioPago;
    private ServicioCarrito servicioCarrito;
    private ServicioMail servicioMail;
    private ServicioCompraHechaImpl servicioOrdenCompra;

    @BeforeEach
    public void init() {
        this.repositorioCompraHecha = mock(RepositorioCompraHecha.class);
        this.repositorioCarrito = mock(RepositorioCarrito.class);
        this.servicioPago = mock(ServicioPago.class);
        this.servicioCarrito = mock(ServicioCarrito.class);
        this.servicioMail = mock(ServicioMail.class);
        this.servicioOrdenCompra = new ServicioCompraHechaImpl(repositorioCompraHecha, repositorioCarrito,  servicioPago, servicioCarrito, servicioMail);
    }

    @Test
    public void deberiaCrearResumenCompraAPartirDeCarritoCorrectamente() throws CarritoVacioException, CarritoNoEncontradoException, PagoNoAprobadoException {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@ejemplo.com");
        Carrito carrito = new Carrito(usuario);
        carrito.setId(1L);
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        obra1.setId(1L);
        obra2.setId(2L);

        FormatoObra formatoObra1 = new FormatoObra(obra1, Formato.ORIGINAL, 2001.0, 5);
        obra1.agregarFormato(formatoObra1);

        FormatoObra formatoObra2 = new FormatoObra(obra2, Formato.ORIGINAL, 2001.0, 5);
        obra2.agregarFormato(formatoObra2);

        Pago pago = new Pago(true, 1L,EstadoPago.APROBADO, "MercadoPago");

        carrito.agregarItem(obra1, Formato.ORIGINAL, 2001.0);
        carrito.agregarItem(obra2, Formato.ORIGINAL, 2001.0);
        carrito.setEstado(EstadoCarrito.ACTIVO);

        when(servicioPago.consultarEstadoDePago(1L)).thenReturn(pago);
        when(repositorioCarrito.obtenerPorId(1L)).thenReturn(carrito);

        CompraHecha compraEsperada = new CompraHecha();
        compraEsperada.setUsuario(usuario);
        compraEsperada.setCarrito(carrito);
        compraEsperada.setPrecioFinal(carrito.getTotal());
        compraEsperada.setPagoId(pago.getIdTransaccion());

        when(repositorioCompraHecha.guardar(compraEsperada)).thenReturn(compraEsperada);

        CompraHecha ordenCreada = this.servicioOrdenCompra.crearResumenCompraAPartirDeCarrito(carrito, pago.getIdTransaccion());

        assertThat(ordenCreada.getUsuario().getEmail(), is(compraEsperada.getUsuario().getEmail()));
        assertThat(ordenCreada.getPrecioFinal(), is(compraEsperada.getPrecioFinal()));
        assertThat(ordenCreada.getPagoId(), is(compraEsperada.getPagoId()));
        verify(servicioMail, times(1))
                .enviarMailConfirmacionCompra(
                        ArgumentMatchers.any(Usuario.class),
                        ArgumentMatchers.any(CompraHecha.class),
                        anyList()
                );
    }

    @Test
    public void deberiaConvertirItemCarritoEnItemCompraCorrectamente() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);

        Obra obra = new Obra();
        obra.setId(1L);
        FormatoObra formatoObra = new FormatoObra(obra, Formato.ORIGINAL, 1000.0, 5);
        obra.agregarFormato(formatoObra);

        ItemCarrito itemCarrito = new ItemCarrito(carrito, obra, Formato.ORIGINAL, 1000.0);
        itemCarrito.setCantidad(2);

        List<ItemCarrito> itemsCarrito = new ArrayList<>();
        itemsCarrito.add(itemCarrito);

        List<ItemCompra> itemsCompra = servicioOrdenCompra.convertirItemCarritoAItemOrden(itemsCarrito);

        // Asserts
        assertThat(itemsCompra.size(), is(1));
        ItemCompra itemCompra = itemsCompra.get(0);
        assertThat(itemCompra.getObra().getId(), is(obra.getId()));
        assertThat(itemCompra.getFormato(), is(Formato.ORIGINAL));
        assertThat(itemCompra.getCantidad(), is(2));
        assertThat(itemCompra.getPrecioUnitario(), is(1000.0));
        assertThat(itemCompra.getSubtotal(), is(2000.0));
    }


    @Test
    public void enCasoDeQueElCarritoConElQueQuieroHacerLaOrdenDeCompraEsteSinItemsDebeDarCarritoVacioException() throws CarritoVacioException {
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setEstado(EstadoCarrito.ACTIVO);
        carrito.setItems(Collections.emptyList());

        Pago pago = new Pago(true, 1L,EstadoPago.APROBADO, "MercadoPago");
        when(servicioPago.consultarEstadoDePago(1L)).thenReturn(pago);
        when(repositorioCarrito.obtenerPorId(1L)).thenReturn(carrito);

        try {
            this.servicioOrdenCompra.crearResumenCompraAPartirDeCarrito(carrito,pago.getIdTransaccion());
            fail("Se esperaba CarritoVacioException pero no fue lanzada");
        } catch (CarritoVacioException | CarritoNoEncontradoException e) {

        } catch (PagoNoAprobadoException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void enCasoDeQueElCarritoConElQueQuieroHacerLaOrdenDeCompraNoExistaDebeDarCarritoNoEncontradoException() throws CarritoNoEncontradoException {
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setEstado(EstadoCarrito.ACTIVO);

        Obra obra1 = new Obra();
        obra1.setId(1L);
        FormatoObra formatoObra1 = new FormatoObra(obra1, Formato.ORIGINAL, 2001.0, 5);
        obra1.agregarFormato(formatoObra1);
        carrito.agregarItem(obra1, Formato.ORIGINAL, 2001.0);

        Pago pago = new Pago(true, 1L,EstadoPago.APROBADO, "MercadoPago");
        when(servicioPago.consultarEstadoDePago(1L)).thenReturn(pago);
        when(repositorioCarrito.obtenerPorId(1L)).thenReturn(null);

        assertThrows(CarritoNoEncontradoException.class, () ->
                servicioOrdenCompra.crearResumenCompraAPartirDeCarrito(carrito, pago.getIdTransaccion()));

    }

    @Test
    public void enCasoDeQueElPagoNoEsteAprobadoDebeDarPagoNoAprobadoException() throws PagoNoAprobadoException {
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setEstado(EstadoCarrito.ACTIVO);

        Obra obra1 = new Obra();
        obra1.setId(1L);
        FormatoObra formatoObra1 = new FormatoObra(obra1, Formato.ORIGINAL, 2001.0, 5);
        obra1.agregarFormato(formatoObra1);
        carrito.agregarItem(obra1, Formato.ORIGINAL, 2001.0);

        Pago pago = new Pago(false, 1L,EstadoPago.RECHAZADO, "MercadoPago");
        when(servicioPago.consultarEstadoDePago(1L)).thenReturn(pago);

        try {
            this.servicioOrdenCompra.crearResumenCompraAPartirDeCarrito(carrito,pago.getIdTransaccion());
            fail("Se esperaba PagoNoAprobadoException pero no fue lanzada");
        } catch (PagoNoAprobadoException e) {

        } catch (CarritoNoEncontradoException | CarritoVacioException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deberiaLanzarIllegalStateExceptionSiCarritoNoEstaActivo() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        carrito.setId(1L);
        carrito.setEstado(EstadoCarrito.FINALIZADO);

        Obra obra1 = new Obra();
        obra1.setId(1L);
        FormatoObra formatoObra1 = new FormatoObra(obra1, Formato.ORIGINAL, 2001.0, 5);
        obra1.agregarFormato(formatoObra1);
        carrito.agregarItem(obra1, Formato.ORIGINAL, 2001.0);

        Pago pago = new Pago(true, 1L, EstadoPago.APROBADO, "MercadoPago");

        when(servicioPago.consultarEstadoDePago(1L)).thenReturn(pago);
        when(repositorioCarrito.obtenerPorId(1L)).thenReturn(carrito);

        IllegalStateException expected = assertThrows(IllegalStateException.class,
                () -> servicioOrdenCompra.crearResumenCompraAPartirDeCarrito(carrito, pago.getIdTransaccion())
        );
        assertThat(expected.getMessage(), is("No se pudo crear la compra: el carrito no está activo o no existe."));
    }

    @Test
    public void deberiaObtenerPorIdCorrectamente(){
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        CompraHecha compra = new CompraHecha();
        compra.setId(1L);
        compra.setUsuario(usuario);
        compra.setPrecioFinal(200.0);

        when(repositorioCompraHecha.obtenerPorId(1L)).thenReturn(compra);

        CompraHecha resultado = servicioOrdenCompra.obtenerCompraPorId(1L);

        assertThat(resultado, is(compra));

    }

    @Test
    public void deberiaObtenerComprasPorUsuarioCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        CompraHecha compra1 = new CompraHecha();
        compra1.setId(1L);
        compra1.setUsuario(usuario);
        compra1.setPrecioFinal(200.0);

        CompraHecha compra2 = new CompraHecha();
        compra2.setId(2L);
        compra2.setUsuario(usuario);
        compra2.setPrecioFinal(300.0);


        when(repositorioCompraHecha.obtenerTodasPorUsuario(usuario)).thenReturn(Arrays.asList(compra1, compra2));

        List<CompraHecha> resultado = servicioOrdenCompra.obtenerComprasPorUsuario(usuario);

        assertThat(resultado.size(), is(2));
        assertThat(resultado, contains(compra1, compra2));

    }

    @Test
    public void deberiaObtenerItemsDeCompraCorrectamente() throws CarritoNoEncontradoException, CarritoVacioException {
        Carrito carrito = new Carrito();

        CompraHecha compra1 = new CompraHecha();
        compra1.setId(1L);
        compra1.setPrecioFinal(200.0);

        Obra obra1 = new Obra();
        obra1.setId(1L);
        FormatoObra formatoObra1 = new FormatoObra(obra1, Formato.ORIGINAL, 2001.0, 5);
        obra1.agregarFormato(formatoObra1);

        Obra obra2 = new Obra();
        obra2.setId(2L);
        FormatoObra formatoObra2 = new FormatoObra(obra2, Formato.DIGITAL, 1500.0, 10);
        obra2.agregarFormato(formatoObra2);

        carrito.agregarItem(obra1, Formato.ORIGINAL, 2001.0);
        carrito.agregarItem(obra2, Formato.ORIGINAL, 2001.0);

        carrito.setEstado(EstadoCarrito.ACTIVO);

        ItemCompra item1 = new ItemCompra(carrito.getItems().get(0));
        ItemCompra item2 = new ItemCompra(carrito.getItems().get(1));

        compra1.setItems(List.of(item1, item2));
        when(repositorioCompraHecha.obtenerPorId(compra1.getId())).thenReturn(compra1);

        List<ItemCompra> resultado = servicioOrdenCompra.obtenerItems(compra1.getId());

        assertThat(resultado.size(), is(2));
        assertThat(resultado, contains(item1, item2));

    }





}
