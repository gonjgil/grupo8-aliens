package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Artista;
import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.ServicioBusqueda;
import com.tallerwebi.dominio.ServicioBusquedaImpl;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronResultadosException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorBusquedaTest {

    @Test
    public void queAlBuscarUnaObraPorTituloSeListenLasObrasQueIncluyanElTextoIngresadoEnElTitulo() {
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Obra obra3 = new Obra();
        Obra obra4 = new Obra();

        obra1.setId(1L);
        obra1.setTitulo("Paisaje Abstracto");
        obra2.setId(2L);
        obra2.setTitulo("Abstracto Azul");
        obra3.setId(3L);
        obra3.setTitulo("Simple Skyline");
        obra4.setId(4L);
        obra4.setTitulo("Abstract Concept");

        ServicioBusqueda servicioBusqueda = mock(ServicioBusqueda.class);
        ControladorBusqueda controladorBusqueda = new ControladorBusqueda(servicioBusqueda);
        String palabraBuscada = "abstract";

        when(servicioBusqueda.buscarObrasPorString(palabraBuscada)).thenReturn(List.of(obra1,obra2,obra4));
        when(servicioBusqueda.buscarArtistaPorNombre(palabraBuscada))
                .thenThrow(new NoSeEncontraronResultadosException(""));

        ResponseEntity<Map<String, Object>> respuesta = controladorBusqueda.buscar(palabraBuscada);
        assertThat(200, is(equalTo(respuesta.getStatusCodeValue())));

        List<Obra> obras = (List<Obra>) respuesta.getBody().get("obras");
        assertThat(obras, is(notNullValue()));
        assertThat(obras, hasSize(3));
        assertThat(obras, containsInAnyOrder(obra1, obra2, obra4));

        List<Artista> artistas = (List<Artista>) respuesta.getBody().get("artistas");
        assertThat(artistas, is(notNullValue()));
        assertThat(artistas, is(empty()));

        verify(servicioBusqueda, times(1)).buscarObrasPorString(palabraBuscada);
        verify(servicioBusqueda, times(1)).buscarArtistaPorNombre(palabraBuscada);
    }
}
