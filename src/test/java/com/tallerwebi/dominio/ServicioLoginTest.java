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

    private ServicioLoginImpl servicioLogin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicioLogin = new ServicioLoginImpl(repositorioUsuario);
    }

    // ----------------------------------------------------------
    // consultarUsuario
    // ----------------------------------------------------------
    @Test
    void queConsulteUsuarioExistenteYDevuelvaEntidad() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@mail.com");
        usuario.setPassword("123");

        when(repositorioUsuario.buscarUsuario("test@mail.com", "123")).thenReturn(usuario);

        Usuario resultado = servicioLogin.consultarUsuario("test@mail.com", "123");

        assertThat(resultado, is(usuario));
        verify(repositorioUsuario, times(1)).buscarUsuario("test@mail.com", "123");
    }

    @Test
    void queConsulteUsuarioInexistenteYDevuelvaNull() {
        when(repositorioUsuario.buscarUsuario("no@mail.com", "clave")).thenReturn(null);

        Usuario resultado = servicioLogin.consultarUsuario("no@mail.com", "clave");

        assertThat(resultado, is((Usuario) null));
        verify(repositorioUsuario, times(1)).buscarUsuario("no@mail.com", "clave");
    }

    // ----------------------------------------------------------
    // registrar
    // ----------------------------------------------------------
    @Test
    void queRegistreUsuarioNuevoCorrectamente() throws UsuarioExistente {
        Usuario usuario = new Usuario();
        usuario.setEmail("nuevo@mail.com");
        usuario.setPassword("123");

        when(repositorioUsuario.buscar("nuevo@mail.com")).thenReturn(null);

        servicioLogin.registrar(usuario);

        verify(repositorioUsuario, times(1)).buscar("nuevo@mail.com");
        verify(repositorioUsuario, times(1)).guardar(usuario);
    }

    @Test
    void queNoPermitaRegistrarUsuarioExistenteYLanceExcepcion() {
        Usuario usuario = new Usuario();
        usuario.setEmail("existente@mail.com");
        usuario.setPassword("123");

        when(repositorioUsuario.buscar("existente@mail.com")).thenReturn(usuario);

        assertThrows(UsuarioExistente.class, () -> servicioLogin.registrar(usuario));

        verify(repositorioUsuario, times(1)).buscar("existente@mail.com");
        verify(repositorioUsuario, never()).guardar(any());
    }
}
