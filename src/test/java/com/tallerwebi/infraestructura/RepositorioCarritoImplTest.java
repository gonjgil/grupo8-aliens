package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.EstadoCarrito;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class RepositorioCarritoImplTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Query<Carrito> query;
    private RepositorioCarritoImpl repositorioCarrito;

    private Usuario usuario;
    private Carrito carrito;

    @BeforeEach
    public void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        query = mock(Query.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        repositorioCarrito = new RepositorioCarritoImpl(sessionFactory);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("juan@mail.com");

        carrito = new Carrito(usuario);
        carrito.setId(10L);
        carrito.setEstado(EstadoCarrito.ACTIVO);
    }

    @Test
    public void queGuardeUnCarritoCorrectamente() {
        repositorioCarrito.guardar(carrito);

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).saveOrUpdate(carrito);
    }

    @Test
    public void queObtengaCarritoPorId() {
        when(session.createQuery(anyString(), eq(Carrito.class))).thenReturn(query);
        when(query.setParameter(eq("id"), anyLong())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(carrito);

        Carrito resultado = repositorioCarrito.obtenerPorId(10L);

        verify(session, times(1)).createQuery(contains("FROM Carrito c LEFT JOIN FETCH c.items"), eq(Carrito.class));
        assertThat(resultado, is(carrito));
    }

    @Test
    public void queDevuelvaNullSiNoExisteCarritoPorId() {
        when(session.createQuery(anyString(), eq(Carrito.class))).thenReturn(query);
        when(query.setParameter(eq("id"), anyLong())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);

        Carrito resultado = repositorioCarrito.obtenerPorId(99L);
        assertThat(resultado, is(nullValue()));
    }

    @Test
    public void queObtengaCarritoActivoPorUsuario() {
        when(session.createQuery(anyString(), eq(Carrito.class))).thenReturn(query);
        when(query.setParameter(eq("usuarioId"), anyLong())).thenReturn(query);
        when(query.setParameter(eq("estado"), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(carrito);

        Carrito resultado = repositorioCarrito.obtenerCarritoActivoPorUsuario(1L);

        verify(session, times(1)).createQuery(contains("c.usuario.id = :usuarioId AND c.estado = :estado"), eq(Carrito.class));
        assertThat(resultado.getEstado(), is(EstadoCarrito.ACTIVO));
    }

    @Test
    public void queDevuelvaNullSiNoHayCarritoActivo() {
        when(session.createQuery(anyString(), eq(Carrito.class))).thenReturn(query);
        when(query.setParameter(eq("usuarioId"), anyLong())).thenReturn(query);
        when(query.setParameter(eq("estado"), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);

        Carrito resultado = repositorioCarrito.obtenerCarritoActivoPorUsuario(2L);
        assertThat(resultado, is(nullValue()));
    }

    @Test
    public void queCreeYGuardeUnCarritoParaUsuario() {
        doNothing().when(session).saveOrUpdate(any());

        Carrito nuevo = repositorioCarrito.crearCarritoParaUsuario(usuario);

        verify(session, times(1)).saveOrUpdate(any());
        assertThat(nuevo.getUsuario(), is(usuario));
        assertThat(nuevo.getEstado(), is(EstadoCarrito.ACTIVO));
    }

    @Test
    public void queElimineUnCarritoCorrectamente() {
        repositorioCarrito.eliminar(carrito);
        verify(session, times(1)).delete(carrito);
    }

    @Test
    public void queActualiceElEstadoDeUnCarrito() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(eq("estado"), any())).thenReturn(query);
        when(query.setParameter(eq("id"), anyLong())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        repositorioCarrito.actualizarEstado(10L, EstadoCarrito.FINALIZADO);

        verify(session, times(1)).createQuery(contains("UPDATE Carrito SET estado"));
        verify(query, times(1)).setParameter("estado", EstadoCarrito.FINALIZADO);
        verify(query, times(1)).setParameter("id", 10L);
        verify(query, times(1)).executeUpdate();
    }

    @Test
    public void queLanceExcepcionSiGuardarFalla() {
        doThrow(RuntimeException.class).when(session).saveOrUpdate(any());

        try {
            repositorioCarrito.guardar(carrito);
        } catch (RuntimeException e) {
            assertThat(e, instanceOf(RuntimeException.class));
        }
    }

    @Test
    public void queLanceExcepcionSiEliminarFalla() {
        doThrow(RuntimeException.class).when(session).delete(any());

        try {
            repositorioCarrito.eliminar(carrito);
        } catch (RuntimeException e) {
            assertThat(e, instanceOf(RuntimeException.class));
        }
    }
}

