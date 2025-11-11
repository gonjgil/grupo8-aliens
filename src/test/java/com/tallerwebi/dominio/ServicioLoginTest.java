package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicioImpl.ServicioLoginImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ServicioLoginImplTest {

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private ServicioPassword servicioPassword;

    private ServicioLoginImpl servicioLogin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicioLogin = new ServicioLoginImpl(repositorioUsuario, servicioPassword);
    }

    // ----------------------------------------------------------
    // consultarUsuario
    // ----------------------------------------------------------
    @Test
    void queConsulteUsuarioExistenteYDevuelvaEntidad() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@mail.com");
        usuario.setPassword("hash123");

        when(repositorioUsuario.buscarUsuario("test@mail.com")).thenReturn(usuario);
        when(servicioPassword.verificarPassword("123", "hash123")).thenReturn(true);

        Usuario resultado = servicioLogin.consultarUsuario("test@mail.com", "123");

        assertThat(resultado, is(usuario));
        verify(repositorioUsuario, times(1)).buscarUsuario("test@mail.com");
        verify(servicioPassword, times(1)).verificarPassword("123", "hash123");
    }

    @Test
    void queConsulteUsuarioInexistenteYDevuelvaNull() {
        when(repositorioUsuario.buscarUsuario("no@mail.com")).thenReturn(null);

        Usuario resultado = servicioLogin.consultarUsuario("no@mail.com", "clave");

        assertThat(resultado, is((Usuario) null));
        verify(repositorioUsuario, times(1)).buscarUsuario("no@mail.com");
    }

    // ----------------------------------------------------------
    // registrar
    // ----------------------------------------------------------
    @Test
    void queRegistreUsuarioNuevoCorrectamente() throws UsuarioExistente {
        Usuario usuario = new Usuario();
        usuario.setEmail("nuevo@mail.com");
        usuario.setPassword("123");

        when(repositorioUsuario.buscarUsuario("nuevo@mail.com")).thenReturn(null);
        when(servicioPassword.hashearPassword("123")).thenReturn("hash123");

        servicioLogin.registrar(usuario);

        verify(repositorioUsuario, times(1)).buscarUsuario("nuevo@mail.com");
        verify(servicioPassword, times(1)).hashearPassword("123");
        verify(repositorioUsuario, times(1)).guardar(usuario);
        assertThat(usuario.getPassword(), is("hash123"));
    }

    @Test
    void queNoPermitaRegistrarUsuarioExistenteYLanceExcepcion() {
        Usuario usuario = new Usuario();
        usuario.setEmail("existente@mail.com");
        usuario.setPassword("123");

        when(repositorioUsuario.buscarUsuario("existente@mail.com")).thenReturn(usuario);

        assertThrows(UsuarioExistente.class, () -> servicioLogin.registrar(usuario));

        verify(repositorioUsuario, times(1)).buscarUsuario("existente@mail.com");
        verify(repositorioUsuario, never()).guardar(any());
        verify(servicioPassword, never()).hashearPassword(any());
    }
}
