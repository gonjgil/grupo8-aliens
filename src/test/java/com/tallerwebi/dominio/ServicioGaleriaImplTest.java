package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoHayObrasExistentes;
import com.tallerwebi.infraestructura.ObraDto;
import com.tallerwebi.infraestructura.ServicioGaleriaImpl;

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
    public void obtener_deberiaLanzarExcepcionSiNoHayObras()  throws NoHayObrasExistentes {
        RepositorioObra repositorioObra = Mockito.mock(RepositorioObra.class);
        Mockito.when(repositorioObra.obtenerTodas()).thenReturn(Collections.emptyList());

        ServicioGaleriaImpl servicio = new ServicioGaleriaImpl(repositorioObra);

        assertThrows(NoHayObrasExistentes.class, servicio::obtener);
    }
}