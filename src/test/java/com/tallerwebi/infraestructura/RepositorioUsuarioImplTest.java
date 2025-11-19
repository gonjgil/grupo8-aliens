package com.tallerwebi.infraestructura;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.tallerwebi.dominio.entidades.Usuario;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepositorioUsuarioImplTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Query<Usuario> query;
    private Criteria criteria;
    private RepositorioUsuarioImpl repositorioUsuario;

    @BeforeEach
    public void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        query = mock(Query.class);
        criteria = mock(Criteria.class);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
    }

    @Test
    public void queBusqueUsuarioPorEmailYLoEncuentre() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@mail.com");

        when(session.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(eq("email"), eq("test@mail.com"))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(usuario);

        Usuario resultado = repositorioUsuario.buscarUsuario("test@mail.com");

        assertThat(resultado, is(usuario));
        verify(session, times(1)).createQuery(contains("FROM Usuario u"), eq(Usuario.class));
        verify(query, times(1)).setParameter("email", "test@mail.com");
        verify(query, times(1)).uniqueResult();
    }

    @Test
    public void queBusqueUsuarioPorEmailYNoEncuentre() {
        when(session.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);

        Usuario resultado = repositorioUsuario.buscarUsuario("no@mail.com");

        assertThat(resultado, is(nullValue()));
        verify(session, times(1)).createQuery(contains("FROM Usuario u"), eq(Usuario.class));
        verify(query, times(1)).uniqueResult();
    }

    @Test
    public void queGuardeOActualiceUnUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("nuevo@mail.com");

        repositorioUsuario.guardar(usuario);

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).saveOrUpdate(usuario);
    }

    @Test
    public void queModifiqueUnUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setEmail("existente@mail.com");

        repositorioUsuario.modificar(usuario);

        verify(session, times(1)).update(usuario);
    }

    @Test
    public void queElimineUnUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        repositorioUsuario.eliminar(usuario);

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).delete(usuario);
    }

}
