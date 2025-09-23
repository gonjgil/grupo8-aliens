package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioObra;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorObraTest {

    @Test
    public void verObra_deberiaMostrarVistaConDatosDeLaObra() throws Exception {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        Obra obra = mock(Obra.class);
        ObraDto obraDto = new ObraDto(obra);
        obraDto.setId(1L);
        obraDto.setTitulo("Obra A");
        obraDto.setAutor("Autor A");
        obraDto.setDescripcion("Lorem Ipsum");
        
        when(servicioGaleria.obtenerPorId(1L)).thenReturn(obraDto);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria);

        ModelAndView modelAndView = controladorObra.verObra(1L);

        assertThat(modelAndView.getViewName(), is(equalTo("obra")));
        assertThat(modelAndView.getModel().get("obra"), is(equalTo(obraDto)));
    }
}
