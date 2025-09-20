package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.excepcion.NoHayObrasExistentes;
import com.tallerwebi.infraestructura.ObraDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControladorGaleriaTest {

    @Test
    public void mostrarGaleria_deberiaRetornarListaVaciaSiNoHayObras() throws NoHayObrasExistentes {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);

        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria);
        doThrow(NoHayObrasExistentes.class).when(servicioGaleria).obtener();

        ModelAndView modelAndView = controladorGaleria.mostrarGaleria();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("galeria"));
        List<ObraDto> obrasDtoObtenidas = (List<ObraDto>) modelAndView.getModel().get("obras");
        assertThat(obrasDtoObtenidas.size(), equalTo(0));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("No hay obras."));
    }

    @Test
    public void siHay4obrasDeberiaRetornar4obras() {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ObraDto obraDto1 = mock(ObraDto.class);
        ObraDto obraDto2 = mock(ObraDto.class);
        ObraDto obraDto3 = mock(ObraDto.class);
        ObraDto obraDto4 = mock(ObraDto.class);
        List<ObraDto> obrasDto = new ArrayList<>();
        obrasDto.add(obraDto1);
        obrasDto.add(obraDto2);
        obrasDto.add(obraDto3);
        obrasDto.add(obraDto4);
        when(servicioGaleria.obtener()).thenReturn(obrasDto);

        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria);

        // ejecucion
        ModelAndView modelAndView = controladorGaleria.mostrarGaleria();

        // verificacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("galeria"));
        List<ObraDto> obrasDtoObtenidas = (List<ObraDto>) modelAndView.getModel().get("obras");
        assertThat(obrasDtoObtenidas.size(), equalTo(4));
        assertThat(modelAndView.getModel().get("exito").toString(), equalToIgnoringCase("Hay obras."));
    }

    @Test
    public void alMostrarseLaGaleriaDeberianMostrarseTresListasDiferentes() {
        ServicioGaleria servicioGaleria = Mockito.mock(ServicioGaleria.class);

        List<ObraDto> randomObras = new ArrayList<>();
        List<ObraDto> autorObras = new ArrayList<>();
        List<ObraDto> temaObras = new ArrayList<>();

        Mockito.when(servicioGaleria.ordenarRandom()).thenReturn(randomObras);
        Mockito.when(servicioGaleria.obtenerPorAutor(Mockito.anyString())).thenReturn(autorObras);
        Mockito.when(servicioGaleria.obtenerPorCategoria(Mockito.anyString())).thenReturn(temaObras);

        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria);

        ModelAndView modelAndView = controladorGaleria.mostrarGaleria();

        assertEquals("galeria", modelAndView.getViewName());
        assertEquals(randomObras, modelAndView.getModel().get("randomObras"));
        assertEquals(autorObras, modelAndView.getModel().get("autorObras"));
        assertEquals(temaObras, modelAndView.getModel().get("temaObras"));
    }

}
