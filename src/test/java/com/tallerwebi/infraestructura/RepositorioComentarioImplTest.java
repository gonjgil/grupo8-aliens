package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Comentario;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioComentario;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static java.util.function.Predicate.isEqual;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
@Transactional
class RepositorioComentarioTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioComentario repositorioComentario;

    @BeforeEach
    public  void setUp() {
        this.repositorioComentario = new RepositorioComentarioImpl(sessionFactory);
    }

    @Test
    void deberiaGuardarComentario() {
        Comentario comentario = new Comentario();
        comentario.setContenido("Hola mundo");

        repositorioComentario.guardar(comentario);

        assertNotNull(comentario.getId());
    }

    @Test
    void deberiaObtenerComentariosDeUnaObra() {
        Usuario usuario = new Usuario();
        sessionFactory.getCurrentSession().save(usuario);

        Obra obra = new Obra();
        sessionFactory.getCurrentSession().save(obra);

        Comentario c = new Comentario();
        c.setUsuario(usuario);
        c.setObra(obra);
        c.setContenido("Test");
        sessionFactory.getCurrentSession().save(c);

        List<Comentario> comentarios = repositorioComentario.obtenerPorObra(obra.getId());

        assertThat(comentarios.size(), is(equalTo(1)));
        assertThat(comentarios.get(0).getContenido(), is(equalTo("Test")));
    }
}

