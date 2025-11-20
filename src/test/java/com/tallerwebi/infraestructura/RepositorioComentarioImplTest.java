package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Comentario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

public class RepositorioComentarioImplTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Query<Comentario> query;
    private RepositorioComentarioImpl repositorioComentario;

    @BeforeEach
    public void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        query = mock(Query.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        repositorioComentario = new RepositorioComentarioImpl(sessionFactory);
    }

    @Test
    public void queGuardeUnComentarioCorrectamente() {
        Comentario comentario = new Comentario();

        repositorioComentario.guardar(comentario);

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).save(comentario);
    }

    @Test
    public void queObtengaComentariosPorObraCorrectamente() {
        Long obraId = 5L;
        List<Comentario> comentarios = Arrays.asList(new Comentario(), new Comentario());

        when(session.createQuery(anyString(), eq(Comentario.class))).thenReturn(query);
        when(query.setParameter("obraId", obraId)).thenReturn(query);
        when(query.getResultList()).thenReturn(comentarios);

        List<Comentario> resultado = repositorioComentario.obtenerPorObra(obraId);

        verify(session, times(1))
                .createQuery(
                        contains("FROM Comentario c JOIN FETCH c.usuario WHERE c.obra.id = :obraId ORDER BY c.fecha ASC"),
                        eq(Comentario.class)
                );

        verify(query, times(1)).setParameter("obraId", obraId);
        verify(query, times(1)).getResultList();

        assertEquals(2, resultado.size());
    }

    @Test
    public void queDevuelvaListaVaciaSiNoHayComentarios() {
        Long obraId = 9L;

        when(session.createQuery(anyString(), eq(Comentario.class))).thenReturn(query);
        when(query.setParameter("obraId", obraId)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<Comentario> resultado = repositorioComentario.obtenerPorObra(obraId);

        assertThat(resultado, hasSize(0));

    }
}

