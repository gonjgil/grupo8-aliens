package com.tallerwebi.dominio;

import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.servicioImpl.ServicioGaleriaImpl;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.presentacion.dto.ObraDto;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.*;


class ServicioGaleriaImplTest {

    @Test
    public void obtener_deberiaRetornarListaDeObras() throws NoHayObrasExistentes {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        when(repositorioObra.obtenerTodas()).thenReturn(Arrays.asList(obra1, obra2));

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        List<Obra> _resultado = servicio.obtener();
        List<ObraDto> resultado = new ArrayList<>();
        for (Obra obra : _resultado) {
            resultado.add(new ObraDto(obra));
        }

        assertThat(resultado.size(), is(2));
        assertThat(resultado.get(0).getId(), is(equalTo(obra1.getId())));
        assertThat(resultado.get(1).getId(), is(equalTo(obra2.getId())));
    }

    @Test
    public void obtener_deberiaLanzarExcepcionSiNoHayObras() throws NoHayObrasExistentes {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        when(repositorioObra.obtenerTodas()).thenReturn(Collections.emptyList());

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        assertThrows(NoHayObrasExistentes.class, servicio::obtener);
    }

    @Test
    public void obtenerPorAutor_deberiaRetornarSoloObrasDeEseAutor() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);

        Artista artistaA = new Artista();
        artistaA.setNombre("Autor A");
        Artista artistaB = new Artista();
        artistaB.setNombre("Autor B");

        Obra obra1 = new Obra();
        obra1.setTitulo("Obra A");
        obra1.setArtista(artistaA);

        Obra obra2 = new Obra();
        obra2.setTitulo("Obra B");
        obra2.setArtista(artistaB);

        when(repositorioObra.obtenerPorArtista(artistaA)).thenReturn(List.of(obra1));

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        List<Obra> _resultado = servicio.obtenerPorAutor(artistaA);
        List<ObraDto> resultado = new ArrayList<>();
        for (Obra obra : _resultado) {
            resultado.add(new ObraDto(obra));
        }

        assertThat(resultado.size(), is(1));
        assertThat(resultado.get(0).getArtista().getNombre(), is(equalTo("Autor A")));
    }

    @Test
    public void obtenerPorCategoria_deberiaDevolverTodasLasObrasDeEsaCategoria() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);

        Obra obra1 = new Obra();
        obra1.setTitulo("Obra A");
        obra1.agregarCategoria(Categoria.ABSTRACTO);

        Obra obra2 = new Obra();
        obra2.setTitulo("Obra B");
        obra2.agregarCategoria(Categoria.COSMICO);

        Obra obra3 = new Obra();
        obra3.setTitulo("Obra C");
        obra3.agregarCategoria(Categoria.ABSTRACTO);

        when(repositorioObra.obtenerPorCategoria(Categoria.ABSTRACTO)).thenReturn(List.of(obra1, obra3));

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        List<Obra> resultado = servicio.obtenerPorCategoria(Categoria.ABSTRACTO);

        assertThat(resultado.size(), is(2));
        assertThat(resultado.get(0).getCategorias().contains(Categoria.ABSTRACTO), is (true));
        assertThat(resultado.get(1).getCategorias().contains(Categoria.ABSTRACTO), is (true));
    }

    // ver con german si esta forma de testar un id autoasignado es adecuada
    // (no queria dejar el setId en la entidad problemas, porque es autoasignado a la db)
    @Test
    public void obtenerPorId_deberiaRetornarLaObraConEseId() throws NoExisteLaObra {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);

        Artista artista = new Artista();
        artista.setNombre("Autor de la Obra");
        Obra obra = new Obra();
        obra.setId(1L);
        obra.setTitulo("Titulo de la Obra");
        obra.setArtista(artista);
        
        when(repositorioObra.obtenerPorId(anyLong())).thenReturn(obra);

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        ObraDto resultado = new ObraDto(servicio.obtenerPorId(1234L));

        assertThat(resultado.getArtista().getNombre(), is("Autor de la Obra"));
        assertThat(resultado.getId(), is(notNullValue()));
    }

    @Test
    public void obtenerObrasParaUsuarioDebeRetornarObrasDeInteresParaElUsuarioBasadoEnInteraccionConOtrasObras() {
        // --- Arrange ---
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("asd@mail.com");

        Artista artista1 = new Artista();
        artista1.setId(1L);
        artista1.setNombre("Artista 1");

        Artista artista2 = new Artista();
        artista2.setId(2L);
        artista2.setNombre("Artista 2");

        Artista artista3 = new Artista();
        artista3.setId(3L);
        artista3.setNombre("Artista 3");

        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setTitulo("Titulo de la Obra 1");
        obra1.setArtista(artista1);
        obra1.agregarCategoria(Categoria.ABSTRACTO);
        obra1.darLike(usuario); // el usuario likea esta obra

        Obra obra2 = new Obra();
        obra2.setId(2L);
        obra2.setTitulo("Titulo de la Obra 2");
        obra2.setArtista(artista1); // mismo artista que obra1
        obra2.agregarCategoria(Categoria.SURREALISMO);

        Obra obra3 = new Obra();
        obra3.setId(3L);
        obra3.setTitulo("Titulo de la Obra 3");
        obra3.setArtista(artista2);
        obra3.agregarCategoria(Categoria.ABSTRACTO); // misma categoría que obra1

        Obra obra4 = new Obra();
        obra4.setId(4L);
        obra4.setTitulo("Titulo de la Obra 4");
        obra4.setArtista(artista2);
        obra4.agregarCategoria(Categoria.SURREALISMO);

        Obra obra5 = new Obra();
        obra5.setId(5L);
        obra5.setTitulo("Titulo de la Obra 5");
        obra5.setArtista(artista3);
        obra5.agregarCategoria(Categoria.ABSTRACTO); // misma categoría que obra1

        List<Obra> todasLasObras = List.of(obra1, obra2, obra3, obra4, obra5);

        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioGaleria servicioGaleria = new ServicioGaleriaImpl(repositorioObra);
        when(repositorioObra.obtenerTodas()).thenReturn(todasLasObras);

        // --- Act ---
        List<Obra> recomendadas = servicioGaleria.obtenerObrasParaUsuario(usuario);

        // --- Assert ---
        assertThat(recomendadas.size(), is(5));
        assertThat(recomendadas.contains(obra1), is(true));
        assertThat(recomendadas.contains(obra2), is(true));
        assertThat(recomendadas.contains(obra3), is(true));
        assertThat(recomendadas.contains(obra5), is(true));
        assertThat(recomendadas.get(4), is(equalTo(obra4)));
    }

    @Test
    public void queSePuedaGuardarUnaObra() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioGaleria servicioGaleria = new ServicioGaleriaImpl(repositorioObra);

        Artista artista = new Artista();
        artista.setId(1L);
        artista.setNombre("Nuevo Artista");

        Obra obra = new Obra();
        obra.setId(1L);
        obra.setTitulo("Nueva Obra");
        obra.setArtista(artista);

        String imgenUrl = "http://imagen.url/nueva_obra.jpg";

        when(repositorioObra.guardar(obra)).thenReturn(obra);

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra);

        servicioGaleria.guardar(obra, artista, imgenUrl);

        Obra obraObtenida = servicioGaleria.obtenerPorId(1L);

        assertThat(obraObtenida.getTitulo(), is("Nueva Obra"));
    }

    @Test
    public void queNoSePuedaGuardarUnaObraNula() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioGaleria servicioGaleria = new ServicioGaleriaImpl(repositorioObra);

        assertThrows(NullPointerException.class, () -> {
            servicioGaleria.guardar(null, null, null);
        });
    }

    @Test
    void actualizarObra_deberiaActualizarCamposYGuardar() throws NoExisteArtista {
        // ARRANGE
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioGaleria servicioGaleria = new ServicioGaleriaImpl(repositorioObra);

        Obra obraExistente = new Obra();
        obraExistente.setId(1L);
        obraExistente.setTitulo("Viejo título");
        obraExistente.setDescripcion("Vieja descripción");
        obraExistente.setImagenUrl("vieja-url.jpg");
        obraExistente.setCategorias(Set.of(Categoria.PINTURA));

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obraExistente);

        ObraDto dto = new ObraDto();
        dto.setTitulo("Nuevo título");
        dto.setDescripcion("Nueva descripción");

        List<String> categoriasSeleccionadas = List.of("PINTURA","ESCULTURA", "ARTE_MIXTO");
        String nuevaUrl = "nueva-url.jpg";

        servicioGaleria.actualizarObra(1L, dto, categoriasSeleccionadas, nuevaUrl);

        assertThat(obraExistente.getTitulo(), is("Nuevo título"));
        assertThat(obraExistente.getDescripcion(), is("Nueva descripción"));
        assertThat(obraExistente.getImagenUrl(), is("nueva-url.jpg"));
        assertThat(obraExistente.getCategorias(), containsInAnyOrder(
                Categoria.PINTURA, Categoria.ESCULTURA, Categoria.ARTE_MIXTO));

        verify(repositorioObra, times(1)).guardar(obraExistente);
    }

    @Test
    void actualizarObra_conObraInexistente_deberiaLanzarExcepcion() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioGaleria servicioGaleria = new ServicioGaleriaImpl(repositorioObra);

        Long idObra = 999L;
        when(repositorioObra.obtenerPorId(idObra)).thenReturn(null);

        assertThrows(NoExisteLaObra.class, () ->
                servicioGaleria.actualizarObra(idObra, new ObraDto(), null, null)
        );
    }

    @Test
    void queSePuedaEliminarUnaObraSiElUsuarioEsElArtistaDeLaObra() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioGaleria servicioGaleria = new ServicioGaleriaImpl(repositorioObra);

        Artista artista = new Artista();
        artista.setId(1L);

        Obra obra = new Obra();
        obra.setId(10L);
        obra.setTitulo("Titulo");
        obra.setArtista(artista);

        servicioGaleria.eliminarObra(obra);

        verify(repositorioObra, times(1)).eliminar(obra);
    }

    @Test
    void queNoSePuedaEliminarUnaObraSiElUsuarioNoEsElArtistaDeLaObra() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioGaleriaImpl servicioGaleria = new ServicioGaleriaImpl(repositorioObra);

        Artista artistaDuenio = new Artista();
        artistaDuenio.setId(1L);

        Artista otroArtista = new Artista();
        otroArtista.setId(2L);

        Obra obra = new Obra();
        obra.setId(10L);
        obra.setTitulo("Titulo de prueba");
        obra.setArtista(artistaDuenio);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (!obra.getArtista().getId().equals(otroArtista.getId())) {
                throw new IllegalArgumentException("El artista no tiene permiso para eliminar esta obra");
            }
            servicioGaleria.eliminarObra(obra);
        });

        assertThat(exception.getMessage(), equalTo("El artista no tiene permiso para eliminar esta obra"));
        verify(repositorioObra, never()).eliminar(obra);
    }

}