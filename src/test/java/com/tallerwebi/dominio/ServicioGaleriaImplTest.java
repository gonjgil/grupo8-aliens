package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.presentacion.ObraDto;
import com.tallerwebi.dominio.ServicioGaleriaImpl;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


class ServicioGaleriaImplTest {

    @Test
    public void obtener_deberiaRetornarListaDeObras() throws NoHayObrasExistentes {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        when(repositorioObra.obtenerTodas()).thenReturn(Arrays.asList(obra1, obra2));

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        List<ObraDto> resultado = servicio.obtener();

        assertThat(resultado.size(), is(2));
        assertThat(resultado.get(0).getId(), is(equalTo(obra1.getId())));
        assertThat(resultado.get(1).getId(), is(equalTo(obra2.getId())));
    }

    @Test
    public void obtener_deberiaLanzarExcepcionSiNoHayObras() throws NoHayObrasExistentes {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        when(repositorioObra.obtenerTodas()).thenReturn(Collections.emptyList());

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        // VER CON EL PROFESOR COMO TESTEAR EXCEPCIONES CON HAMCREST
        assertThrows(NoHayObrasExistentes.class, servicio::obtener);
    }

    @Test
    public void obtenerPorAutor_deberiaRetornarSoloObrasDeEseAutor() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);

        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setTitulo("Obra A");
        obra1.setAutor("Autor A");

        Obra obra2 = new Obra();
        obra2.setId(2L);
        obra2.setTitulo("Obra B");
        obra2.setAutor("Autor B");

        when(repositorioObra.obtenerPorAutor("Autor A")).thenReturn(List.of(obra1));

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        List<ObraDto> resultado = servicio.obtenerPorAutor("Autor A");

        assertThat(resultado.size(), is(1));
        assertThat(resultado.get(0).getAutor(), is(equalTo("Autor A")));
    }
}