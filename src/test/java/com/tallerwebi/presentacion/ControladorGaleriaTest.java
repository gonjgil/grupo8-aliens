package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioCaja;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.excepcion.NoHayObrasExistentes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;

public class ControladorGaleriaTest {

    @Test
    public void mostrarObras_deberiaRetornarListaVaciaSiNoHayObras() throws NoHayObrasExistentes {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);

        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria);
        doThrow(NoHayObrasExistentes.class).when(servicioGaleria).obtener();

        ModelAndView modelAndView = controladorGaleria.mostarObras();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("galeria"));
        List<ObraDto> obrasDtoObtenidas = (List<ObraDto>) modelAndView.getModel().get("obras");
        assertThat(obrasDtoObtenidas.size(), equalTo(0));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("No hay obras."));
    }

    @Test
    public void siHay4obras_mostrarObras_deberiaRetornar4obras() {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        List<ObraDto> obrasDto = new ArrayList<>();
        obrasDto.add(new ObraDto());
        obrasDto.add(new ObraDto());
        obrasDto.add(new ObraDto());
        obrasDto.add(new ObraDto());
        when(servicioGaleria.obtener()).thenReturn(obrasDto);
        
        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria);
         
        // ejecucion
        ModelAndView  modelAndView = controladorGaleria.mostarObras();
        
        //verificacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("galeria"));
        assertThat(obrasDto.size(), equalTo(4));
        assertThat(modelAndView.getModel().get("exito").toString(), equalToIgnoringCase("Hay obras."));

    }

}
