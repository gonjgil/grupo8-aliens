package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioObra;

public class ControladorObraTest {

    @Test
    public void verObra_deberiaMostrarVistaConDatosDeLaObra() throws Exception {
        ServicioGaleria servicioGaleria = Mockito.mock(ServicioGaleria.class);
        Obra obra = Mockito.mock(Obra.class);
        ObraDto obraDto = new ObraDto(obra);
        obraDto.setId(1L);
        obraDto.setTitulo("Obra A");
        obraDto.setAutor("Autor A");
        obraDto.setDescripcion("Lorem Ipsum");
        
        Mockito.when(servicioGaleria.obtenerPorId(1L)).thenReturn(obraDto);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria);

        ModelAndView modelAndView = controladorObra.verObra(1L);

        assertEquals("obra", modelAndView.getViewName());
        assertEquals(obraDto, modelAndView.getModel().get("obra"));
    }
}
