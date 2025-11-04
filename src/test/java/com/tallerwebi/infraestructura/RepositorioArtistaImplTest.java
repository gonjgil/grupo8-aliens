package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioArtista;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
public class RepositorioArtistaImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioArtista repositorioArtista;

    @BeforeEach
    public void init() {
        this.repositorioArtista = new RepositorioArtistaImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnArtistaPorId() {
        Artista artista = new Artista();
        artista.setNombre("Artista Test");
        repositorioArtista.guardar(artista);
        Long id = artista.getId();
        Artista artistaBuscado = repositorioArtista.buscarArtistaPorId(id);
        assertThat(artistaBuscado.getNombre(), is(equalTo("Artista Test")));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnArtista() {
        Artista artista = new Artista();
        artista.setNombre("Nuevo Artista");
        repositorioArtista.guardar(artista);
        Long id = artista.getId();
        Artista artistaGuardado = repositorioArtista.buscarArtistaPorId(id);
        assertThat(artistaGuardado.getNombre(), is(equalTo("Nuevo Artista")));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaModificarUnArtista() {
        Artista artista = new Artista();
        artista.setNombre("Artista Original");
        repositorioArtista.guardar(artista);
        Long id = artista.getId();
        artista.setNombre("Artista Modificado");
        repositorioArtista.modificar(artista);
        Artista artistaModificado = repositorioArtista.buscarArtistaPorId(id);
        assertThat(artistaModificado.getNombre(), is(equalTo("Artista Modificado")));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerArtistasPorNombre() {
        Artista artista1 = new Artista();
        artista1.setNombre("Artista 1");
        repositorioArtista.guardar(artista1);

        Artista artista2 = new Artista();
        artista2.setNombre("Artista 2");
        repositorioArtista.guardar(artista2);

        Artista artista3 = new Artista();
        artista3.setNombre("Otro nombre");
        repositorioArtista.guardar(artista3);

        List<Artista> artistasEncontrados = repositorioArtista.obtenerPorNombre("Artista");
        assertThat(artistasEncontrados.size(), is(equalTo(2)));
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSeEncuentreUnArtistaInexistente() {
        Artista artistaBuscado = repositorioArtista.buscarArtistaPorId(9999L);
        assertThat(artistaBuscado, is(equalTo(null)));
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSePuedaModificarUnArtistaInexistente() {
        Artista artista = new Artista();
        artista.setId(9999L);
        artista.setNombre("Artista Inexistente");
        try {
            repositorioArtista.modificar(artista);
        } catch (Exception e) {
            assertThat(e.getMessage().contains("No row with the given identifier exists"), is(true));
        }
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnArtistaPorSuUsuario() {
        RepositorioUsuario repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
        Usuario usuario = new Usuario();
        usuario.setEmail("asd@mail.com");
        repositorioUsuario.guardar(usuario);

        Artista artista = new Artista();
        artista.setNombre("Artista Usuario");
        artista.setUsuario(usuario);

        repositorioArtista.guardar(artista);

        Artista artistaEncontrado = repositorioArtista.buscarPorUsuario(1L);
        assertThat(artistaEncontrado.getNombre(), is(equalTo("Artista Usuario")));
    }


}
