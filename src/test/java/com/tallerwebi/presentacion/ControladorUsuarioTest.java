package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Direccion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.PasswordActualIncorrectoException;
import com.tallerwebi.dominio.excepcion.PasswordIdenticoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class ControladorUsuarioTest {

    private ServicioUsuario servicioUsuario;
    private ServicioPerfilArtista servicioPerfilArtista;
    private HttpSession session;
    private ControladorUsuario controladorUsuario;

    @BeforeEach
    public void setUp() {
        servicioUsuario = mock(ServicioUsuario.class);
        servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        session = mock(HttpSession.class);

        controladorUsuario = new ControladorUsuario(servicioUsuario, servicioPerfilArtista);
    }

    @Test
    public void queSiNoHayUsuarioLogueadoRedirijaALogin() {
        // Arrange
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        // Act
        ModelAndView mav = controladorUsuario.verDirecciones(session);

        // Assert
        assertThat(mav.getViewName(), is("redirect:/login"));
    }

    @Test
    public void queConUsuarioLogueadoSeMuestreLaVistaDeDirecciones() {
        ServicioUsuario servicioUsuario = mock(ServicioUsuario.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);

        ControladorUsuario controladorUsuario =
                new ControladorUsuario(servicioUsuario, servicioPerfilArtista);

        Usuario usuario = new Usuario();
        Direccion d1 = new Direccion();
        Direccion d2 = new Direccion();
        usuario.agregarDireccion(d1);
        usuario.agregarDireccion(d2);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ModelAndView mav = controladorUsuario.verDirecciones(session);

        assertEquals("direcciones", mav.getViewName());
        assertEquals(usuario, mav.getModel().get("usuario"));
        assertEquals(usuario.getDirecciones(), mav.getModel().get("direcciones"));
    }


    @Test
    public void queAlCrearUnaDireccionConUsuarioLogueadoSeMuestreElFormularioVacio() {
        Usuario usuario = new Usuario();
        usuario.agregarDireccion(new Direccion());

        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ModelAndView mav = controladorUsuario.crearDireccion(session);

        assertThat(mav.getViewName(), is("crear-editar_direccion"));
        assertThat(mav.getModel().get("usuario"), is(usuario));
        assertThat(usuario.getDirecciones().size(), is(1));
    }

    @Test
    public void queAlEditarUnaDireccionExistenteSeMuestreElFormularioConLosDatosAnteriores() {
        ServicioUsuario servicioUsuario = mock(ServicioUsuario.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);

        ControladorUsuario controladorUsuario =
                new ControladorUsuario(servicioUsuario, servicioPerfilArtista);

        Long idDireccion = 10L;

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Direccion direccion = new Direccion();
        direccion.setId(idDireccion);
        direccion.setNombreCalle("Mitre");
        direccion.setAltura("123");

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);

        when(servicioUsuario.buscarDireccionDelUsuario(usuario, idDireccion))
                .thenReturn(direccion);

        ModelAndView mav = controladorUsuario.editarDireccion(idDireccion, session);

        assertEquals("crear-editar_direccion", mav.getViewName());
        assertEquals(usuario, mav.getModel().get("usuario"));
        assertEquals(direccion, mav.getModel().get("direccion"));

        verify(servicioUsuario).buscarDireccionDelUsuario(usuario, idDireccion);
    }

    @Test
    public void queAlGuardarUnaDireccionSeLlameAlServicioYRedirijaADirecciones() {
        ServicioUsuario servicioUsuario = mock(ServicioUsuario.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);

        ControladorUsuario controladorUsuario =
                new ControladorUsuario(servicioUsuario, servicioPerfilArtista);

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Direccion nuevaDireccion = new Direccion();
        nuevaDireccion.setNombreCalle("Sarmiento");
        nuevaDireccion.setAltura("123");

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ModelAndView mav = controladorUsuario.guardarDireccion(nuevaDireccion, session);

        assertEquals("redirect:/usuario/direcciones", mav.getViewName());

        verify(servicioUsuario, times(1)).guardarOEditarDireccion(usuario, nuevaDireccion);
        verify(session).setAttribute("usuarioLogueado", usuario);
    }

    @Test
    public void queAlEliminarUnaDireccionSeLlameAlServicioYRedirijaADirecciones() {
        ServicioUsuario servicioUsuario = mock(ServicioUsuario.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);

        ControladorUsuario controladorUsuario =
                new ControladorUsuario(servicioUsuario, servicioPerfilArtista);

        Long idDireccion = 5L;

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ModelAndView mav = controladorUsuario.eliminarDireccion(idDireccion, session);

        assertEquals("redirect:/usuario/direcciones", mav.getViewName());

        verify(servicioUsuario, times(1))
                .eliminarDireccion(usuario, idDireccion);

        verify(session, times(1))
                .setAttribute("usuarioLogueado", usuario);
    }

    @Test
    public void queAlMarcarUnaDireccionPredeterminadaSoloEsaQuedeComoPredeterminada() {
        ServicioUsuario servicioUsuario = mock(ServicioUsuario.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);

        ControladorUsuario controladorUsuario =
                new ControladorUsuario(servicioUsuario, servicioPerfilArtista);

        Long idDireccion = 3L;

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        List<Direccion> direcciones = new ArrayList<>();
        Direccion d1 = new Direccion();
        d1.setId(1L);
        d1.setPredeterminada(false);
        Direccion d2 = new Direccion();
        d2.setId(3L);
        d2.setPredeterminada(false);
        Direccion d3 = new Direccion();
        d3.setId(5L);
        d3.setPredeterminada(false);

        direcciones.add(d1);
        direcciones.add(d2);
        direcciones.add(d3);
        usuario.setDirecciones(direcciones);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);

        doAnswer(invocation -> {
            usuario.getDirecciones().forEach(d -> d.setPredeterminada(d.getId().equals(idDireccion)));
            return null;
        }).when(servicioUsuario).marcarDireccionPredeterminada(usuario, idDireccion);

        ModelAndView mav = controladorUsuario.marcarComoPredeterminada(idDireccion, session);

        assertEquals("redirect:/usuario/direcciones", mav.getViewName());
        assertTrue(d2.getPredeterminada());
        assertFalse(d1.getPredeterminada());
        assertFalse(d3.getPredeterminada());

        verify(servicioUsuario).marcarDireccionPredeterminada(usuario, idDireccion);
    }

    @Test
    public void queLaInformacionPersonalSePuedaActualizarCorrectamente() {
        ServicioUsuario servicioUsuario = mock(ServicioUsuario.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);

        ControladorUsuario controladorUsuario =
                new ControladorUsuario(servicioUsuario, servicioPerfilArtista);

        Usuario usuarioSesion = new Usuario();
        usuarioSesion.setId(1L);
        Usuario usuarioForm = new Usuario();
        usuarioForm.setEmail("nuevo@ejemplo.com");

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1L);
        usuarioActualizado.setEmail("nuevo@ejemplo.com");

        when(servicioUsuario.actualizarDatosUsuario(usuarioSesion, usuarioForm))
                .thenReturn(usuarioActualizado);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuarioSesion);

        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        String resultado = controladorUsuario.guardarUsuario(usuarioForm, session, redirectAttributes);

        assertEquals("redirect:/usuario/info", resultado);
        verify(servicioUsuario, times(1)).actualizarDatosUsuario(usuarioSesion, usuarioForm);
        verify(session, times(1)).setAttribute("usuarioLogueado", usuarioActualizado);
        verify(redirectAttributes, times(1)).addFlashAttribute("mensajeExito", "Perfil actualizado con éxito!");
    }

    @Test
    public void queSiLasContrasenasNoCoincidenSeMuestreErrorEnLaVistaConfiguracion() throws PasswordActualIncorrectoException, PasswordIdenticoException {
        ServicioUsuario servicioUsuario = mock(ServicioUsuario.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);

        ControladorUsuario controladorUsuario =
                new ControladorUsuario(servicioUsuario, servicioPerfilArtista);

        Usuario usuarioSesion = new Usuario();
        usuarioSesion.setId(1L);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuarioSesion);

        String passwordActual = "vieja123";
        String nuevoPassword = "abc123";
        String confirmarPassword = "xyz987";

        ModelAndView mav = controladorUsuario.cambiarPassword(
                passwordActual, nuevoPassword, confirmarPassword, session
        );

        assertEquals("usuario_configuracion", mav.getViewName());
        assertEquals("Las contraseñas no coinciden.", mav.getModel().get("error"));
        verify(servicioUsuario, never()).cambiarPassword(any(), anyString(), anyString());
    }

    @Test
    public void queAlCambiarPasswordCorrectamenteSeMuestreMensajeDeExito() throws PasswordActualIncorrectoException, PasswordIdenticoException {
        ServicioUsuario servicioUsuario = mock(ServicioUsuario.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);

        ControladorUsuario controladorUsuario =
                new ControladorUsuario(servicioUsuario, servicioPerfilArtista);

        Usuario usuarioSesion = new Usuario();
        usuarioSesion.setId(1L);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuarioSesion);

        String passwordActual = "vieja123";
        String nuevoPassword = "abc123";
        String confirmarPassword = "abc123";

        ModelAndView mav = controladorUsuario.cambiarPassword(
                passwordActual, nuevoPassword, confirmarPassword, session
        );

        assertEquals("usuario_configuracion", mav.getViewName());
        assertEquals("La contraseña fue actualizada correctamente.", mav.getModel().get("exito"));
        verify(servicioUsuario).cambiarPassword(usuarioSesion, passwordActual, nuevoPassword);
    }


    @Test
    public void queAlEliminarLaCuentaSeElimineCorrectamenteYLaSesionSeInvalide() {
        ServicioUsuario servicioUsuario = mock(ServicioUsuario.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);

        ControladorUsuario controladorUsuario =
                new ControladorUsuario(servicioUsuario, servicioPerfilArtista);

        Usuario usuarioSesion = new Usuario();
        usuarioSesion.setId(5L);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuarioSesion);

        ModelAndView mav = controladorUsuario.eliminarCuenta(session);

        verify(servicioUsuario).eliminarCuenta(usuarioSesion);
        verify(session).invalidate();

        assertEquals("redirect:/", mav.getViewName());
    }

    @Test
    public void verPerfilDeUsuarioRedirigeALoginSiUsuarioEsNull() {
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        ModelAndView mav = controladorUsuario.verPerfilDeUsuario(session);

        assertEquals("redirect:/login", mav.getViewName());
    }

    @Test
    public void verPerfilDeUsuarioMuestraPerfilSiUsuarioLogueado() {
        Usuario usuario = new Usuario();
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);
        Artista artista = new Artista();
        when(servicioPerfilArtista.obtenerArtistaPorUsuario(usuario)).thenReturn(artista);

        ModelAndView mav = controladorUsuario.verPerfilDeUsuario(session);

        assertEquals("perfil_usuario", mav.getViewName());
        assertEquals(usuario, mav.getModel().get("usuario"));
        assertEquals(artista, mav.getModel().get("artistaUsuario"));
    }

    @Test
    public void verInformacionPersonalRedirigeALoginSiUsuarioEsNull() {
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        ModelAndView mav = controladorUsuario.verInformacionPersonal(session);

        assertEquals("redirect:/login", mav.getViewName());
    }

    @Test
    public void verInformacionPersonalMuestraVistaSiUsuarioLogueado() {
        Usuario usuario = new Usuario();
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ModelAndView mav = controladorUsuario.verInformacionPersonal(session);

        assertEquals("usuario_info", mav.getViewName());
        assertEquals(usuario, mav.getModel().get("usuario"));
    }

    @Test
    public void configurarCuentaRedirigeALoginSiUsuarioEsNull() {
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        ModelAndView mav = controladorUsuario.configurarCuenta(session);

        assertEquals("redirect:/login", mav.getViewName());
    }

    @Test
    public void configurarCuentaMuestraVistaSiUsuarioLogueado() {
        Usuario usuario = new Usuario();
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);

        ModelAndView mav = controladorUsuario.configurarCuenta(session);

        assertEquals("usuario_configuracion", mav.getViewName());
        assertEquals(usuario, mav.getModel().get("usuario"));
    }

    @Test
    public void queCrearDireccionRedirijaALoginSiNoHayUsuario() {
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        ModelAndView mav = controladorUsuario.crearDireccion(session);

        assertEquals("redirect:/login", mav.getViewName());
    }

    @Test
    public void queEliminarCuentaRedirijaALoginSiNoHayUsuario() {
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        ModelAndView mav = controladorUsuario.eliminarCuenta(session);

        assertEquals("redirect:/login", mav.getViewName());
    }


    @Test
    public void queEliminarDireccionRedirijaALoginSiNoHayUsuario() {
        Long idDireccion = 5L;
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        ModelAndView mav = controladorUsuario.eliminarDireccion(idDireccion, session);

        assertEquals("redirect:/login", mav.getViewName());
    }

    @Test
    public void queAlGuardarDireccionRedirijaALoginSiNoHayUsuario() {

        when(session.getAttribute("usuarioLogueado")).thenReturn(null);
        Direccion nuevaDireccion = new Direccion();

        ModelAndView mav = controladorUsuario.guardarDireccion(nuevaDireccion, session);

        assertEquals("redirect:/login", mav.getViewName());
    }

    @Test
    public void queAlMarcarDireccionComoPredeterminadaRedirijaALoginSiNoHayUsuario() {
        Long idDireccion = 5L;
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        ModelAndView mav = controladorUsuario.marcarComoPredeterminada(idDireccion, session);

        assertEquals("redirect:/login", mav.getViewName());
    }

    @Test
    public void queAlIntentarEditarDireccionRedirijaALoginSiNoHayUsuario() {
        Long idDireccion = 5L;
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        ModelAndView mav = controladorUsuario.editarDireccion(idDireccion, session);

        assertEquals("redirect:/login", mav.getViewName());
    }

    @Test
    public void queAlIntentarEditarLaDireccionRedirijaAVistaUsuarioSiNoHayDireccion() {

        Long idDireccion = 99L;

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);
        when(servicioUsuario.buscarDireccionDelUsuario(usuario, idDireccion))
                .thenReturn(null);

        ModelAndView mav = controladorUsuario.editarDireccion(idDireccion, session);

        // Assert
        assertEquals("redirect:/usuario", mav.getViewName());
    }

    @Test
    public void queAlIntentarCambiarPasswordRedirijaALoginSiNoHayUsuario() {
        String passwordActual = "vieja123";
        String nuevoPassword = "abc123";
        String confirmarPassword = "abc123";
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        ModelAndView mav = controladorUsuario.cambiarPassword(passwordActual,nuevoPassword, confirmarPassword, session);

        assertEquals("redirect:/login", mav.getViewName());
    }

    @Test
    public void queAlFallarElCambioDePasswordMuestreMensajeDeError() throws PasswordActualIncorrectoException, PasswordIdenticoException {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);

        // Simula excepción en el servicio
        doThrow(new RuntimeException("Error al cambiar la contraseña"))
                .when(servicioUsuario)
                .cambiarPassword(usuario, "vieja", "nueva");


        ModelAndView mav = controladorUsuario.cambiarPassword(
                "vieja",
                "nueva",
                "nueva",
                session
        );

        assertEquals("usuario_configuracion", mav.getViewName());
        assertEquals(usuario, mav.getModel().get("usuario"));
        assertEquals("Error al cambiar la contraseña", mav.getModel().get("error"));

        verify(servicioUsuario, times(1))
                .cambiarPassword(usuario, "vieja", "nueva");
    }

    @Test
    public void queAlGuardarUsuarioRedirijaALoginSiNoEstaLogueado() {
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        Usuario usuarioForm = new Usuario();

        String vista = controladorUsuario.guardarUsuario(usuarioForm, session, mock(RedirectAttributes.class));

        assertEquals("redirect:/login", vista);
    }
}
