package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioMail;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import com.tallerwebi.presentacion.dto.ItemCarritoDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

public class ControladorCarritoTest {

    private RedirectAttributes redirectAttributesMock;
    private HttpSession sessionMock;
    private ServicioCarrito servicioCarritoMock;
    private ServicioMail servicioMailMock;

    @BeforeEach
    public void setUp() {
        this.redirectAttributesMock = mock(RedirectAttributes.class);
        this.sessionMock = mock(HttpSession.class);
        this.servicioCarritoMock = mock(ServicioCarrito.class);
        this.servicioMailMock = mock(ServicioMail.class);

        Usuario usuario = mock(Usuario.class);
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);
    }

    @Test
    public void verCarrito_deberiaRetornarCarritoConItemsCuandoUsuarioLogueado() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");
        
        Direccion direccion = new Direccion();
        direccion.setCodigoPostal("1200");
        usuario.agregarDireccion(direccion);

        List<ItemCarritoDto> items = new ArrayList<>();

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);
        when(servicioCarritoMock.obtenerItems(usuario)).thenReturn(items);
        when(servicioCarritoMock.calcularPrecioTotalCarrito(usuario)).thenReturn(100.0);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        ModelAndView resultado = controlador.verCarrito(sessionMock);

        assertThat(resultado.getViewName(), equalToIgnoringCase("carrito"));
        assertThat(resultado.getModel().get("usuario"), is(usuario));
        assertThat(resultado.getModel().get("items"), is(items));
        assertThat(resultado.getModel().get("total"), is(100.0));
        assertThat(resultado.getModel().get("costoEnvio"), is(8000.0));
        assertThat(resultado.getModel().get("totalFinal"), is(8100.0));
    }

    @Test
    public void verCarrito_debeRedirectALoginCuandoNoHayUsuarioLogueado() {
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(null);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        ModelAndView resultado = controlador.verCarrito(sessionMock);

        assertThat(resultado.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void verCarrito_deberiaCalcularCostoEnvioCero_cuandoNoHayDireccionPredeterminada() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");

        List<ItemCarritoDto> items = new ArrayList<>();
        
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);
        when(servicioCarritoMock.obtenerItems(usuario)).thenReturn(items);
        when(servicioCarritoMock.calcularPrecioTotalCarrito(usuario)).thenReturn(0.0);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        ModelAndView resultado = controlador.verCarrito(sessionMock);

        assertThat(resultado.getModel().get("costoEnvio"), is(0.0));
        assertThat(resultado.getModel().get("totalFinal"), is(0.0));
    }

    @Test
    public void agregarAlCarrito_deberiaAgregarObraConExito() throws NoExisteLaObra, NoHayStockSuficiente {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.agregarAlCarrito(1L, "DIGITAL", redirectAttributesMock, sessionMock);

        assertThat(resultado, is("redirect:/obra/1"));
    }

    @Test
    public void agregarAlCarrito_debeRedirectALoginCuandoUsuarioNoLogueado() {
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(null);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.agregarAlCarrito(1L, "DIGITAL", redirectAttributesMock, sessionMock);

        assertThat(resultado, is("redirect:/login"));
    }

    @Test
    public void agregarAlCarrito_debeHandlearNoExisteLaObra() throws NoExisteLaObra, NoHayStockSuficiente {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);
        doThrow(NoExisteLaObra.class).when(servicioCarritoMock).agregarObraAlCarrito(usuario, 1L, Formato.DIGITAL);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.agregarAlCarrito(1L, "DIGITAL", redirectAttributesMock, sessionMock);

        assertThat(resultado, is("redirect:/obra/1"));
    }

    @Test
    public void agregarAlCarrito_debeHandlearNoHayStockSuficiente() throws NoExisteLaObra, NoHayStockSuficiente {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);
        doThrow(NoHayStockSuficiente.class).when(servicioCarritoMock).agregarObraAlCarrito(usuario, 1L, Formato.DIGITAL);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.agregarAlCarrito(1L, "DIGITAL", redirectAttributesMock, sessionMock);

        assertThat(resultado, is("redirect:/obra/1"));
    }

    @Test
    public void agregarAlCarrito_debeHandlearFormatoInvalido() throws NoExisteLaObra, NoHayStockSuficiente {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);
        doThrow(IllegalArgumentException.class).when(servicioCarritoMock).agregarObraAlCarrito(usuario, 1L, null);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.agregarAlCarrito(1L, "INVALIDO", redirectAttributesMock, sessionMock);

        assertThat(resultado, is("redirect:/obra/1"));
    }

    @Test
    public void aumentarCantidad_debeAumentarConExito() throws NoHayStockSuficiente {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.aumentarCantidad(1L, sessionMock, null, "DIGITAL", redirectAttributesMock);

        assertThat(resultado, is("redirect:/carrito"));
    }

    @Test
    public void aumentarCantidad_debeRedirectAObraConParametro() throws NoHayStockSuficiente {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.aumentarCantidad(1L, sessionMock, "obra", "DIGITAL", redirectAttributesMock);

        assertThat(resultado, is("redirect:/obra/1"));
    }

    @Test
    public void disminuirCantidad_debeDisminuirConExito() throws NoHayStockSuficiente {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.disminuirCantidad(1L, "DIGITAL", sessionMock);

        assertThat(resultado, is("redirect:/carrito"));
    }

    @Test
    public void disminuirCantidad_debeRedirectALoginCuandoUsuarioNoLogueado() {
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(null);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.disminuirCantidad(1L, "DIGITAL", sessionMock);

        assertThat(resultado, is("redirect:/login"));
    }

    @Test
    public void eliminarDelCarrito_debeEliminarConExito() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.eliminarDelCarrito(1L, "DIGITAL", sessionMock, redirectAttributesMock);

        assertThat(resultado, is("redirect:/carrito"));
    }

    @Test
    public void eliminarDelCarrito_debeRedirectALoginCuandoUsuarioNoLogueado() {
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(null);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.eliminarDelCarrito(1L, "DIGITAL", sessionMock, redirectAttributesMock);

        assertThat(resultado, is("redirect:/login"));
    }

    @Test
    public void vaciarCarrito_debeVaciarConExito() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.vaciarCarrito(sessionMock, redirectAttributesMock);

        assertThat(resultado, is("redirect:/carrito"));
    }

    @Test
    public void vaciarCarrito_debeRedirectALoginCuandoUsuarioNoLogueado() {
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(null);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.vaciarCarrito(sessionMock, redirectAttributesMock);

        assertThat(resultado, is("redirect:/login"));
    }

    @Test
    public void obtenerContadorCarrito_debeRetornarCountCuandoHayItems() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Obra obra1 = mock(Obra.class);
        when(obra1.getId()).thenReturn(1L);
        ItemCarrito item1 = mock(ItemCarrito.class);
        when(item1.getCantidad()).thenReturn(2);
        when(item1.getObra()).thenReturn(obra1);

        Obra obra2 = mock(Obra.class);
        when(obra2.getId()).thenReturn(2L);
        ItemCarrito item2 = mock(ItemCarrito.class);
        when(item2.getCantidad()).thenReturn(3);
        when(item2.getObra()).thenReturn(obra2);

        List<ItemCarritoDto> items = new ArrayList<>();
        ItemCarritoDto dto1 = new ItemCarritoDto(item1);
        ItemCarritoDto dto2 = new ItemCarritoDto(item2);
        items.add(dto1);
        items.add(dto2);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);
        when(servicioCarritoMock.obtenerItems(usuario)).thenReturn(items);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.obtenerContadorCarrito(sessionMock);

        assertThat(resultado, is("{\"count\": 5}"));
    }

    @Test
    public void obtenerContadorCarrito_debeRetornarCeroCuandoNoHayUsuarioLogueado() {
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(null);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.obtenerContadorCarrito(sessionMock);

        assertThat(resultado, is("{\"count\": 0}"));
    }

    @Test
    public void finalizarCompra_debeFinalizarConExito() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Direccion direccion = new Direccion();
        direccion.setPredeterminada(true);
        usuario.agregarDireccion(direccion);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.finalizarCompra(sessionMock, redirectAttributesMock);

        assertThat(resultado, is("redirect:/galeria"));
    }

    @Test
    public void finalizarCompra_debeRedirectALoginCuandoUsuarioNoLogueado() {
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(null);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.finalizarCompra(sessionMock, redirectAttributesMock);

        assertThat(resultado, is("redirect:/login"));
    }

    @Test
    public void finalizarCompra_debeRedirectACarritoCuandoNoDireccionPredeterminada() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ControladorCarrito controlador = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        String resultado = controlador.finalizarCompra(sessionMock, redirectAttributesMock);

        assertThat(resultado, is("redirect:/carrito"));
    }

}
