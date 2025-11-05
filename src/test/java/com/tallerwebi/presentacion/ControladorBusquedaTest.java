package com.tallerwebi.presentacion;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tallerwebi.dominio.ServicioBusqueda;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronResultadosException;
import com.tallerwebi.presentacion.dto.ObraDto;
import com.tallerwebi.presentacion.dto.PerfilArtistaDTO;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class ControladorBusquedaTest {

    @Test
    public void queAlBuscarUnaObraPorTituloSeListenLasObrasQueIncluyanElTextoIngresadoEnElTitulo() {
        Obra obra1 = new Obra(); obra1.setId(1L); obra1.setTitulo("Paisaje Abstracto");
        Obra obra2 = new Obra(); obra2.setId(2L); obra2.setTitulo("Abstracto Azul");
        Obra obra3 = new Obra(); obra3.setId(3L); obra3.setTitulo("Simple Skyline");
        Obra obra4 = new Obra(); obra4.setId(4L); obra4.setTitulo("Abstract Concept");

        ServicioBusqueda servicioBusqueda = mock(ServicioBusqueda.class);
        ControladorBusqueda controladorBusqueda = new ControladorBusqueda(servicioBusqueda);
        String palabraBuscada = "abstract";

        when(servicioBusqueda.buscarObrasPorString(palabraBuscada))
                .thenReturn(List.of(obra1, obra2, obra4));
        when(servicioBusqueda.buscarArtistaPorNombre(palabraBuscada))
                .thenThrow(new NoSeEncontraronResultadosException(""));

        ResponseEntity<Map<String, Object>> respuesta = controladorBusqueda.buscar(palabraBuscada);
        assertThat(respuesta.getStatusCodeValue(), is(200));

        List<ObraDto> obrasDto = (List<ObraDto>) respuesta.getBody().get("obras");
        assertThat(obrasDto, is(notNullValue()));
        assertThat(obrasDto, hasSize(3));

        // Comparar por campo que importa, por ejemplo id o titulo
        List<Long> idsEsperados = List.of(1L, 2L, 4L);
        List<Long> idsObtenidos = obrasDto.stream()
                .map(ObraDto::getId)
                .collect(Collectors.toList());
        assertThat(idsObtenidos, containsInAnyOrder(idsEsperados.toArray()));

        List<PerfilArtistaDTO> artistas = (List<PerfilArtistaDTO>) respuesta.getBody().get("artistas");
        assertThat(artistas, is(notNullValue()));
        assertThat(artistas, is(empty()));

        verify(servicioBusqueda, times(1)).buscarObrasPorString(palabraBuscada);
        verify(servicioBusqueda, times(1)).buscarArtistaPorNombre(palabraBuscada);
    }

    @Test
    public void queAlBuscarUnArtistaPorNombreSeListenLosArtistasQueIncluyanElTextoIngresadoEnElNombre() {
        // Implementar test similar al anterior pero para artistas
        Artista artista1 = new Artista(); artista1.setId(1L); artista1.setNombre("Juan Perez");
        Artista artista2 = new Artista(); artista2.setId(2L); artista2.setNombre("Juana Gomez");
        ServicioBusqueda servicioBusqueda = mock(ServicioBusqueda.class);
        ControladorBusqueda controladorBusqueda = new ControladorBusqueda(servicioBusqueda);
        String palabraBuscada = "juan";

        when(servicioBusqueda.buscarArtistaPorNombre(palabraBuscada))
                .thenReturn(List.of(artista1, artista2));
        when(servicioBusqueda.buscarObrasPorString(palabraBuscada))
                .thenThrow(new NoSeEncontraronResultadosException(""));
        ResponseEntity<Map<String, Object>> respuesta = controladorBusqueda.buscar(palabraBuscada);
        assertThat(respuesta.getStatusCodeValue(), is(200));
        List<PerfilArtistaDTO> artistasDto = (List<PerfilArtistaDTO>) respuesta.getBody().get("artistas");
        assertThat(artistasDto, is(notNullValue()));
        assertThat(artistasDto, hasSize(2));
        List<Long> idsEsperados = List.of(1L, 2L);
        List<Long> idsObtenidos = artistasDto.stream()
                .map(PerfilArtistaDTO::getId)
                .collect(Collectors.toList());
        assertThat(idsObtenidos, containsInAnyOrder(idsEsperados.toArray()));
        List<ObraDto> obras = (List<ObraDto>) respuesta.getBody().get("obras");
        assertThat(obras, is(notNullValue()));
        assertThat(obras, is(empty()));
        verify(servicioBusqueda, times(1)).buscarObrasPorString(palabraBuscada);
        verify(servicioBusqueda, times(1)).buscarArtistaPorNombre(palabraBuscada);
    }

    @Test
    public void queAlBuscarUnaObraPorCualquierCriterioYNoSeEncuentrenResultadosDevuelvaListasVacias() {
        ServicioBusqueda servicioBusqueda = mock(ServicioBusqueda.class);
        ControladorBusqueda controladorBusqueda = new ControladorBusqueda(servicioBusqueda);
        String palabraBuscada = "inexistente";

        when(servicioBusqueda.buscarObrasPorString(palabraBuscada))
                .thenThrow(new NoSeEncontraronResultadosException(""));
        when(servicioBusqueda.buscarArtistaPorNombre(palabraBuscada))
                .thenThrow(new NoSeEncontraronResultadosException(""));

        ResponseEntity<Map<String, Object>> respuesta = controladorBusqueda.buscar(palabraBuscada);
        assertThat(respuesta.getStatusCodeValue(), is(204));
        assertThat(respuesta.getBody(), is(nullValue()));

        verify(servicioBusqueda, times(1)).buscarObrasPorString(palabraBuscada);
        verify(servicioBusqueda, times(1)).buscarArtistaPorNombre(palabraBuscada);
    }

    @Test
    public void queAlBuscarUnArtistaPorNombreYNoSeEncuentrenResultadosDevuelvaListasVacias() {
        ServicioBusqueda servicioBusqueda = mock(ServicioBusqueda.class);
        ControladorBusqueda controladorBusqueda = new ControladorBusqueda(servicioBusqueda);
        String palabraBuscada = "inexistente";

        when(servicioBusqueda.buscarObrasPorString(palabraBuscada))
                .thenThrow(new NoSeEncontraronResultadosException(""));
        when(servicioBusqueda.buscarArtistaPorNombre(palabraBuscada))
                .thenThrow(new NoSeEncontraronResultadosException(""));

        ResponseEntity<Map<String, Object>> respuesta = controladorBusqueda.buscar(palabraBuscada);
        assertThat(respuesta.getStatusCodeValue(), is(204));
        assertThat(respuesta.getBody(), is(nullValue()));

        verify(servicioBusqueda, times(1)).buscarObrasPorString(palabraBuscada);
        verify(servicioBusqueda, times(1)).buscarArtistaPorNombre(palabraBuscada);
    }

    @Test
    public void queAlObtenerListasVaciasDeObrasYArtistasElControladorRespondaNoContent() {
        ServicioBusqueda servicioBusqueda = mock(ServicioBusqueda.class);
        ControladorBusqueda controladorBusqueda = new ControladorBusqueda(servicioBusqueda);
        String palabraBuscada = "inexistente";

        when(servicioBusqueda.buscarObrasPorString(palabraBuscada))
                .thenThrow(new NoSeEncontraronResultadosException(""));
        when(servicioBusqueda.buscarArtistaPorNombre(palabraBuscada))
                .thenThrow(new NoSeEncontraronResultadosException(""));

        ResponseEntity<Map<String, Object>> respuesta = controladorBusqueda.buscar(palabraBuscada);

        assertThat(respuesta.getStatusCodeValue(), is(204));

        assertThat(respuesta.getBody(), is(nullValue()));

        verify(servicioBusqueda, times(1)).buscarObrasPorString(palabraBuscada);
        verify(servicioBusqueda, times(1)).buscarArtistaPorNombre(palabraBuscada);
    }

    @Test
    public void queAlBuscarObrasExistentesYNoSeEncuentrenArtistasDevuelvaSoloObras() {
        ServicioBusqueda servicioBusqueda = mock(ServicioBusqueda.class);
        ControladorBusqueda controladorBusqueda = new ControladorBusqueda(servicioBusqueda);
        String palabraBuscada = "paisaje";

        Obra obra = new Obra();
        obra.setId(1L);
        obra.setTitulo("Paisaje Abstracto");
        when(servicioBusqueda.buscarObrasPorString(palabraBuscada))
                .thenReturn(List.of(obra));

        when(servicioBusqueda.buscarArtistaPorNombre(palabraBuscada))
                .thenThrow(new NoSeEncontraronResultadosException(""));

        ResponseEntity<Map<String, Object>> respuesta = controladorBusqueda.buscar(palabraBuscada);

        assertThat(respuesta.getStatusCodeValue(), is(200));

        List<ObraDto> obrasDto = (List<ObraDto>) respuesta.getBody().get("obras");
        assertThat(obrasDto, is(notNullValue()));
        assertThat(obrasDto, hasSize(1));
        assertThat(obrasDto.get(0).getTitulo(), is("Paisaje Abstracto"));

        List<PerfilArtistaDTO> artistasDto = (List<PerfilArtistaDTO>) respuesta.getBody().get("artistas");
        assertThat(artistasDto, is(notNullValue()));
        assertThat(artistasDto, is(empty()));

        verify(servicioBusqueda, times(1)).buscarObrasPorString(palabraBuscada);
        verify(servicioBusqueda, times(1)).buscarArtistaPorNombre(palabraBuscada);
    }

    @Test
    public void queAlBuscarArtistasExistentesYNoSeEncuentrenObrasDevuelvaSoloArtistas() {
        ServicioBusqueda servicioBusqueda = mock(ServicioBusqueda.class);
        ControladorBusqueda controladorBusqueda = new ControladorBusqueda(servicioBusqueda);
        String palabraBuscada = "Picasso";

        when(servicioBusqueda.buscarObrasPorString(palabraBuscada))
                .thenThrow(new NoSeEncontraronResultadosException(""));

        Artista artista = new Artista();
        artista.setId(1L);
        artista.setNombre("Pablo Picasso");
        when(servicioBusqueda.buscarArtistaPorNombre(palabraBuscada))
                .thenReturn(List.of(artista));

        ResponseEntity<Map<String, Object>> respuesta = controladorBusqueda.buscar(palabraBuscada);

        assertThat(respuesta.getStatusCodeValue(), is(200));

        List<ObraDto> obrasDto = (List<ObraDto>) respuesta.getBody().get("obras");
        assertThat(obrasDto, is(notNullValue()));
        assertThat(obrasDto, is(empty()));

        List<PerfilArtistaDTO> artistasDto = (List<PerfilArtistaDTO>) respuesta.getBody().get("artistas");
        assertThat(artistasDto, is(notNullValue()));
        assertThat(artistasDto, hasSize(1));
        assertThat(artistasDto.get(0).getNombre(), is("Pablo Picasso"));

        verify(servicioBusqueda, times(1)).buscarObrasPorString(palabraBuscada);
        verify(servicioBusqueda, times(1)).buscarArtistaPorNombre(palabraBuscada);
    }
}
