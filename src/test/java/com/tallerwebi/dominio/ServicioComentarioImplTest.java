package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Comentario;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioComentario;
import com.tallerwebi.dominio.servicioImpl.ServicioComentarioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ServicioComentarioImplTest {

    private RepositorioComentario repositorioComentario;
    private ServicioComentarioImpl servicioComentario;

    @BeforeEach
    public void setUp() {
        this.repositorioComentario = mock(RepositorioComentario.class);
        this.servicioComentario = new ServicioComentarioImpl(repositorioComentario);
    }

    @Test
    public void deberiaGuardarComentarioCorrectamente() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Obra obra = new Obra();
        obra.setId(10L);

        String contenido = "Muy buena obra";

        ArgumentCaptor<Comentario> captor = ArgumentCaptor.forClass(Comentario.class);

        // Act
        servicioComentario.guardarComentario(usuario, obra, contenido);

        // Assert
        verify(repositorioComentario, times(1)).guardar(captor.capture());

        Comentario guardado = captor.getValue();

        assertThat(guardado.getUsuario(), equalTo(usuario));
        assertThat(guardado.getObra(), equalTo(obra));
        assertThat(guardado.getContenido(), equalTo(contenido));
        assertThat(guardado.getFecha(), equalTo(LocalDate.now()));
    }

    @Test
    public void queSePuedanObtenerComentariosPorObra() {
        Long idObra = 1L;

        Comentario c1 = new Comentario();
        c1.setContenido("Buenísimo");
        Comentario c2 = new Comentario();
        c2.setContenido("Excelente obra");

        when(repositorioComentario.obtenerPorObra(idObra))
                .thenReturn(List.of(c1, c2));

        List<Comentario> comentarios = servicioComentario.obtenerComentariosDeObra(idObra);

        assertThat(comentarios.size(), is(equalTo(2)));
        assertThat(comentarios.get(0).getContenido(), is(equalTo("Buenísimo")));
    }

    @Test
    public void queSiNoHayComentariosSeDevuelvaListaVacia() {
        Long idObra = 9L;

        when(repositorioComentario.obtenerPorObra(idObra))
                .thenReturn(List.of());

        List<Comentario> comentarios = servicioComentario.obtenerComentariosDeObra(idObra);

        assertThat(comentarios.isEmpty(), is(true));
    }
}
