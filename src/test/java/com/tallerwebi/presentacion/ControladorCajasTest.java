package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioCaja;
import com.tallerwebi.dominio.excepcion.NoHayCajasExistente;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

public class ControladorCajasTest {

    @Test // analizar si probamos peticion de tipo GET, POST, PUT
    public void dadoQueSePuedenConsultarCajasCuandoLasConsultoSinHaberAgregadoObtengoUnMensajeNoHayCajas() throws NoHayCajasExistente {
        // preaparacion
        ServicioCaja servicioCaja = mock(ServicioCaja.class);
        
        ControladorCajas controladorCajas = new ControladorCajas(servicioCaja);
        doThrow(NoHayCajasExistente.class).when(servicioCaja).obtener();
        
        // ejecucion
        ModelAndView  modelAndView = controladorCajas.mostrarCajas();
        
        //verificacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("cajas"));
        List<CajaDto> cajasDtoObtenidas = (List<CajaDto>) modelAndView.getModel().get("cajas");
        assertThat(cajasDtoObtenidas.size(), equalTo(0));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("No hay cajas."));
    }
    
    @Test
    public void dadoQueExistenCajasCuandoLasConsultoSeMuestran3Cajas() {
        ServicioCaja servicioCaja = mock(ServicioCaja.class);
        List<CajaDto> cajasDto = new ArrayList<>();
        cajasDto.add(new CajaDto());
        cajasDto.add(new CajaDto());
        cajasDto.add(new CajaDto());
        when(servicioCaja.obtener()).thenReturn(cajasDto);
        
        // preaparacion
        ControladorCajas controladorCajas = new ControladorCajas(servicioCaja);
         
        // ejecucion
        ModelAndView  modelAndView = controladorCajas.mostrarCajas();
        
        //verificacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("cajas"));
        assertThat(cajasDto.size(), equalTo(3));
        assertThat(modelAndView.getModel().get("exito").toString(), equalToIgnoringCase("Hay cajas."));

    }
}

