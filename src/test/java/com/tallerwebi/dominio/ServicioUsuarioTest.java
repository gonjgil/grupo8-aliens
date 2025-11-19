package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Direccion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.excepcion.PasswordActualIncorrectoException;
import com.tallerwebi.dominio.excepcion.PasswordIdenticoException;
import com.tallerwebi.dominio.repositorios.RepositorioArtista;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicioImpl.ServicioUsuarioImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioUsuarioTest {

    private final RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
    private final RepositorioArtista repositorioArtista = mock(RepositorioArtista.class);
    private final ServicioPassword servicioPassword = mock(ServicioPassword.class);
    private final RepositorioCarrito repositorioCarrito = mock(RepositorioCarrito.class);

    private final ServicioUsuarioImpl servicioUsuario =
            new ServicioUsuarioImpl(repositorioUsuario, repositorioArtista, servicioPassword, repositorioCarrito);

    @Test
    public void queAlBuscarDireccionDelUsuarioDebeDevolverLaDireccionCorrecta() {
        Usuario usuario = new Usuario();
        Direccion d1 = new Direccion(); d1.setId(1L);
        Direccion d2 = new Direccion(); d2.setId(2L);

        usuario.setDirecciones(List.of(d1, d2));

        Direccion encontrada = servicioUsuario.buscarDireccionDelUsuario(usuario, 2L);

        assertEquals(d2, encontrada);
    }

    @Test
    public void queAlGuardarUnaDireccionSeGuardeCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setDirecciones(new ArrayList<>());

        Direccion dir = new Direccion();
        dir.setNombreCalle("Mitre");

        servicioUsuario.guardarOEditarDireccion(usuario, dir);

        assertEquals(1, usuario.getDirecciones().size());
        verify(repositorioUsuario).modificar(usuario);
    }

    @Test
    public void queAlEditarUnaDireccionSeActualiceCorrectamente() {
        Usuario usuario = new Usuario();
        Direccion original = new Direccion();
        original.setId(10L);
        original.setNombreCalle("Vieja");

        usuario.setDirecciones(new ArrayList<>(List.of(original)));

        Direccion form = new Direccion();
        form.setId(10L);
        form.setNombreCalle("Nueva");

        servicioUsuario.guardarOEditarDireccion(usuario, form);

        assertEquals("Nueva", original.getNombreCalle());
        verify(repositorioUsuario).modificar(usuario);
    }

    @Test
    public void queAlEliminarUnaDireccionSeElimineCorrectamente() {
        Usuario usuario = new Usuario();
        Direccion d1 = new Direccion(); d1.setId(1L);
        Direccion d2 = new Direccion(); d2.setId(2L);

        usuario.setDirecciones(new ArrayList<>(List.of(d1, d2)));

        servicioUsuario.eliminarDireccion(usuario, 2L);

        assertEquals(1, usuario.getDirecciones().size());
        assertEquals(d1, usuario.getDirecciones().get(0));
        verify(repositorioUsuario).modificar(usuario);
    }

    @Test
    public void queAlMarcarUnaDireccionPredeterminadaLasDemasDebenQuedarEnFalse() {
        Usuario usuario = new Usuario();

        Direccion d1 = new Direccion(); d1.setId(1L); d1.setPredeterminada(false);
        Direccion d2 = new Direccion(); d2.setId(2L); d2.setPredeterminada(false);
        usuario.setDirecciones(new ArrayList<>(List.of(d1, d2)));

        servicioUsuario.marcarDireccionPredeterminada(usuario, 2L);

        assertFalse(d1.getPredeterminada());
        assertTrue(d2.getPredeterminada());
        verify(repositorioUsuario).modificar(usuario);
    }

    @Test
    public void queAlCambiarElPasswordSiIngresoPasswordIgualAlActualArrojeUnPasswordIdenticoException() {
        Usuario usuario = new Usuario();
        usuario.setPassword("HASH");

        when(servicioPassword.verificarPassword("actual", "HASH")).thenReturn(true);
        when(servicioPassword.verificarPassword("nuevo", "HASH")).thenReturn(true);

        assertThrows(
                PasswordIdenticoException.class, () -> servicioUsuario.cambiarPassword
                        (usuario, "actual", "nuevo")
        );
    }

    @Test
    public void queAlCambiarElPasswordConPasswordActualIncorrectoArrojePasswordActualIncorrectoException() {
        Usuario usuario = new Usuario();
        usuario.setPassword("HASH");

        when(servicioPassword.verificarPassword("actual", "HASH")).thenReturn(false);

        assertThrows(PasswordActualIncorrectoException.class, () -> servicioUsuario.cambiarPassword
                (usuario, "actual", "nuevo")
        );
    }

    @Test
    public void queAlCambiarElPasswordConDatosValidosSeCambieCorrectamente()
            throws PasswordActualIncorrectoException, PasswordIdenticoException {
        Usuario usuario = new Usuario();
        usuario.setPassword("OLDHASH");

        when(servicioPassword.verificarPassword("actual", "OLDHASH")).thenReturn(true);
        when(servicioPassword.verificarPassword("nuevo", "OLDHASH")).thenReturn(false);
        when(servicioPassword.hashearPassword("nuevo")).thenReturn("NEWHASH");

        servicioUsuario.cambiarPassword(usuario, "actual", "nuevo");

        assertEquals("NEWHASH", usuario.getPassword());
        verify(repositorioUsuario).guardar(usuario);
    }

    @Test
    public void queAlEliminarLaCuentaSeBorreCorrectamente() {
        Usuario usuario = new Usuario();
        Artista artista = new Artista();

        when(repositorioArtista.buscarArtistaPorUsuario(usuario)).thenReturn(artista);

        ServicioUsuarioImpl servicio =
                new ServicioUsuarioImpl(repositorioUsuario, repositorioArtista, servicioPassword, repositorioCarrito);

        servicio.eliminarCuenta(usuario);

        verify(repositorioArtista).eliminar(artista);
        verify(repositorioCarrito).eliminarPorUsuario(usuario);
        verify(repositorioUsuario).eliminar(usuario);
    }

    @Test
    public void queAlActualizarLosDatosDeUsuarioLoHagaCorrectamente() {
        Usuario original = new Usuario();
        Usuario form = new Usuario();

        form.setNombre("Juan");
        form.setEmail("nuevo@test.com");
        form.setTelefono(1L);
        Long telefonoEsperado = 1L;

        servicioUsuario.actualizarDatosUsuario(original, form);

        assertEquals("Juan", original.getNombre());
        assertEquals("nuevo@test.com", original.getEmail());
        assertEquals(telefonoEsperado , original.getTelefono());
        verify(repositorioUsuario).guardar(original);
    }
}
