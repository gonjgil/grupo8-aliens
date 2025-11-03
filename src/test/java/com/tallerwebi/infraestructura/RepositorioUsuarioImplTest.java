package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import org.hibernate.Criteria;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

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
    public void queBusqueUsuarioPorEmailYPasswordYLoEncuentre() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@mail.com");
        usuario.setPassword("123");

        when(session.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(eq("email"), eq("test@mail.com"))).thenReturn(query);
        when(query.setParameter(eq("password"), eq("123"))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(usuario);

        Usuario resultado = repositorioUsuario.buscarUsuario("test@mail.com", "123");

        assertThat(resultado, is(usuario));
        verify(session, times(1)).createQuery(contains("FROM Usuario u"), eq(Usuario.class));
        verify(query, times(1)).setParameter("email", "test@mail.com");
        verify(query, times(1)).setParameter("password", "123");
        verify(query, times(1)).uniqueResult();
    }

    @Test
    public void queBusqueUsuarioPorEmailYPasswordYNoEncuentre() {
        when(session.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);

        Usuario resultado = repositorioUsuario.buscarUsuario("no@mail.com", "clave");

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
    public void queBusqueUsuarioPorEmailYLoEncuentre() {
        Usuario usuario = new Usuario();
        usuario.setEmail("correo@mail.com");

        when(session.createCriteria(Usuario.class)).thenReturn(criteria);
        when(criteria.add(any())).thenReturn(criteria);
        when(criteria.uniqueResult()).thenReturn(usuario);

        Usuario resultado = repositorioUsuario.buscar("correo@mail.com");

        assertThat(resultado, is(usuario));
        verify(session, times(1)).createCriteria(Usuario.class);
        verify(criteria, times(1)).add(any());
        verify(criteria, times(1)).uniqueResult();
    }

    @Test
    public void queBusqueUsuarioPorEmailYNoLoEncuentre() {
        when(session.createCriteria(Usuario.class)).thenReturn(criteria);
        when(criteria.add(any())).thenReturn(criteria);
        when(criteria.uniqueResult()).thenReturn(null);

        Usuario resultado = repositorioUsuario.buscar("noexiste@mail.com");

        assertThat(resultado, is(nullValue()));
        verify(criteria, times(1)).uniqueResult();
    }

    @Test
    public void queModifiqueUnUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setEmail("existente@mail.com");

        repositorioUsuario.modificar(usuario);

        verify(session, times(1)).update(usuario);
    }
}
