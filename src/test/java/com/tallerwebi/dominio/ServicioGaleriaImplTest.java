package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.presentacion.ObraDto;
import com.tallerwebi.dominio.ServicioGaleriaImpl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicioGaleriaImplTest {

    @Test
    public void obtener_deberiaRetornarListaDeObras() throws NoHayObrasExistentes {
        RepositorioObra repositorioObra = Mockito.mock(RepositorioObra.class);
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Mockito.when(repositorioObra.obtenerTodas()).thenReturn(Arrays.asList(obra1, obra2));

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        List<ObraDto> resultado = servicio.obtener();

        assertEquals(2, resultado.size());
        assertEquals(obra1.getId(), resultado.get(0).getId());
        assertEquals(obra2.getId(), resultado.get(1).getId());
    }

    @Test
    public void obtener_deberiaLanzarExcepcionSiNoHayObras() throws NoHayObrasExistentes {
        RepositorioObra repositorioObra = Mockito.mock(RepositorioObra.class);
        Mockito.when(repositorioObra.obtenerTodas()).thenReturn(Collections.emptyList());

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        assertThrows(NoHayObrasExistentes.class, servicio::obtener);
    }

    @Test
    public void obtenerPorAutor_deberiaRetornarSoloObrasDeEseAutor() {
        RepositorioObra repositorioObra = Mockito.mock(RepositorioObra.class);

        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setTitulo("Obra A");
        obra1.setAutor("Autor A");

        Obra obra2 = new Obra();
        obra2.setId(2L);
        obra2.setTitulo("Obra B");
        obra2.setAutor("Autor B");

        List<Obra> obras = Arrays.asList(obra1, obra2);
        Mockito.when(repositorioObra.obtenerPorAutor("Autor A")).thenReturn(List.of(obra1));

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        List<ObraDto> resultado = servicio.obtenerPorAutor("Autor A");

        assertEquals(1, resultado.size());
        assertEquals("Autor A", resultado.get(0).getAutor());
    }
}