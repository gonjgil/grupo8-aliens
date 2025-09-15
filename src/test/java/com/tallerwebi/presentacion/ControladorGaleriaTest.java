package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoDisponibilidad;
import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.ServicioObra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorGaleriaTest {

    private ControladorGaleria controladorGaleria;
    private ServicioObra servicioObraMock;

    @BeforeEach
    public void init() {
        servicioObraMock = mock(ServicioObra.class);
        controladorGaleria = new ControladorGaleria(servicioObraMock);
    }

    @Test
    public void listarObrasDeberiaRetornarVistaGaleriaConObrasSiExistenObrasAlmacenadas() {
        // preparación
        Obra obra1 = new Obra("Obra 1", "Descripción de obra 1", "30 x 20", LocalDate.now(), EstadoDisponibilidad.DISPONIBLE, 10);
        Obra obra2 = new Obra("Obra 2", "Descripción de obra 2", "40 x 30", LocalDate.now(), EstadoDisponibilidad.DISPONIBLE, 5);
        List<Obra> obras = Arrays.asList(obra1, obra2);
        when(servicioObraMock.listarObras()).thenReturn(obras);

        // ejecución
        ModelAndView mav = controladorGaleria.listarObras();

        // validación
        assertThat(mav.getViewName(), equalToIgnoringCase("galeria"));
        assertThat((List<Obra>) mav.getModel().get("obras"), hasSize(2));
        verify(servicioObraMock, times(1)).listarObras();
    }

    @Test
    public void listarObrasDeberiaRetornarVistaGaleriaConListaVaciaSiListarObrasEsNull() {
        // preparación
        when(servicioObraMock.listarObras()).thenReturn(null);

        // ejecución
        ModelAndView mav = controladorGaleria.listarObras();

        // validación
        assertThat(mav.getViewName(), equalToIgnoringCase("galeria"));
        assertThat((List<Obra>) mav.getModel().get("obras"), hasSize(0));
        verify(servicioObraMock, times(1)).listarObras();
    }

}
