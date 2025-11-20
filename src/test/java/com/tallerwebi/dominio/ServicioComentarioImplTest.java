package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Comentario;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioComentario;
import com.tallerwebi.dominio.servicioImpl.ServicioComentarioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class ServicioComentarioImplTest {


    private RepositorioComentario repositorioComentario;
    private ServicioComentario servicioComentario;

    @BeforeEach
    public void setUp() {
        repositorioComentario = mock(RepositorioComentario.class);
        servicioComentario = new ServicioComentarioImpl(repositorioComentario);
    }

    @Test
    public void queSeGuardeUnComentarioConLosDatosCorrectos() {
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        String contenido = "Excelente obra!";

        servicioComentario.guardarComentario(usuario, obra, contenido);

        verify(repositorioComentario).guardar(argThat(comentario ->
                comentario.getUsuario().equals(usuario) &&
                        comentario.getObra().equals(obra) &&
                        comentario.getContenido().equals(contenido) &&
                        comentario.getFecha().equals(LocalDate.now())
        ));
    }

    @Test
    public void queSeObtenganLosComentariosDeUnaObra() {
        Long obraId = 10L;

        Comentario comentario1 = new Comentario();
        Comentario comentario2 = new Comentario();
        List<Comentario> lista = List.of(comentario1, comentario2);

        when(repositorioComentario.obtenerPorObra(obraId)).thenReturn(lista);

        List<Comentario> resultado = servicioComentario.obtenerComentariosDeObra(obraId);

        assertEquals(2, resultado.size());
        verify(repositorioComentario).obtenerPorObra(obraId);
    }

}
