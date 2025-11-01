package com.tallerwebi.dominio;

import com.tallerwebi.dominio.repositorios.RepositorioArtista;
import com.tallerwebi.dominio.servicioImpl.ServicioPerfilArtistaImpl;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.presentacion.dto.PerfilArtistaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioPerfilArtistaImplTest {

    private ServicioPerfilArtistaImpl servicioPerfilArtistaImpl;
    private RepositorioArtista repositorioArtistaMock;

    @BeforeEach
    public void init() {
        repositorioArtistaMock = mock(RepositorioArtista.class);
        servicioPerfilArtistaImpl = new ServicioPerfilArtistaImpl(repositorioArtistaMock);

    }

    @Test
    public void deberiaObtenerPerfilDeArtistaExistente() {
        // Preparación
        Long idArtista = 123L;
        Artista artistaMock = new Artista("Picasso", "Artista cubista", "http://example.com/picasso.jpg");
        artistaMock.setId(idArtista);

        when(repositorioArtistaMock.buscarArtistaPorId(idArtista)).thenReturn(artistaMock);
        // Ejecución
        PerfilArtistaDTO artistaObtenido = servicioPerfilArtistaImpl.obtenerPerfilArtista(idArtista);

        // Validación
        assertThat(artistaObtenido.getId(), is(equalTo(artistaMock.getId())));

    }

    @Test
    public void deberiaLanzarExcepcionCuandoArtistaNoExiste() {
        // Preparación
        Long idArtista = 222L;
        when(repositorioArtistaMock.buscarArtistaPorId(idArtista)).thenReturn(null);
        // Ejecución y validación
        assertThrows(NoExisteArtista.class, () -> servicioPerfilArtistaImpl.obtenerPerfilArtista(idArtista));
    }

    @Test
    public void deberiaActualizarPerfilDeArtistaExistente(){
        // Preparación
        Long idArtista = 333L;
        Artista artistaMock = new Artista("Salvador Dalí", "Surrealista", "http://example.com/dali.jpg");
        artistaMock.setId(idArtista);

        PerfilArtistaDTO datosActualizacion = new PerfilArtistaDTO();
        datosActualizacion.setId(idArtista);
        datosActualizacion.setNombre("Salvador Dalí Nuevo");
        datosActualizacion.setBiografia("Gran pintor surrealista.");
        datosActualizacion.setUrlFotoPerfil("http://example.com/dali_new.jpg");
        datosActualizacion.setUrlFacebook("http://facebook.com/daliart");
        datosActualizacion.setUrlInstagram("http://instagram.com/daliart");

        when(repositorioArtistaMock.buscarArtistaPorId(idArtista)).thenReturn(artistaMock);

        // Ejecución
        servicioPerfilArtistaImpl.actualizarPerfilArtista(datosActualizacion);

        // Validación
        assertThat(artistaMock.getNombre(), equalTo("Salvador Dalí Nuevo"));
        assertThat(artistaMock.getBiografia(), equalTo("Gran pintor surrealista."));
        assertThat(artistaMock.getUrlFotoPerfil(), equalTo("http://example.com/dali_new.jpg"));
        assertThat(artistaMock.getUrlFacebook(), equalTo("http://facebook.com/daliart"));
        assertThat(artistaMock.getUrlInstagram(), equalTo("http://instagram.com/daliart"));
    }

    @Test
    public void deberiaCrearUnNuevoPerfilDeArtista() {
        // Preparación
        PerfilArtistaDTO datosCreacion = new PerfilArtistaDTO();
        datosCreacion.setNombre("Andy Warhol");
        datosCreacion.setBiografia("Pionero del Pop Art.");
        datosCreacion.setUrlFotoPerfil("http://example.com/warhol.jpg");
        datosCreacion.setUrlInstagram("http://instagram.com/warhol");

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setEmail("usuario@prueba.com");

        // Ejecución
        Artista nuevoArtista = servicioPerfilArtistaImpl.crearPerfilArtista(datosCreacion, usuarioMock);

        // Validación
        assertThat(nuevoArtista, is(notNullValue()));
        assertThat(nuevoArtista.getNombre(), equalTo("Andy Warhol"));
        assertThat(nuevoArtista.getBiografia(), equalTo("Pionero del Pop Art."));
        assertThat(nuevoArtista.getUrlFotoPerfil(), equalTo("http://example.com/warhol.jpg"));
        assertThat(nuevoArtista.getUrlInstagram(), equalTo("http://instagram.com/warhol"));
    }
}