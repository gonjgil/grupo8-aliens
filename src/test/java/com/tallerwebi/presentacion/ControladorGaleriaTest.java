package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.ServicioObra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class ControladorGaleriaTest {

    private ControladorGaleria controladorGaleria;
    private ServicioObra servicioObraMock;

    @BeforeEach
    public void init() {
        servicioObraMock = mock(ServicioObra.class);
        controladorGaleria = new ControladorGaleria(servicioObraMock);
    }

    @Test
    public void listarObrasDeberiaRetornarVistaGaleriaConObras() {
        // preparación
        Obra obra1 = new Obra(1L, "Obra 1", "Descripción 1", 100.0);
        Obra obra2 = new Obra(2L, "Obra 2", "Descripción 2", 200.0);
        List<Obra> obras = Arrays.asList(obra1, obra2);
        when(obraServiceMock.listarObras()).thenReturn(obras);

        // ejecución
        ModelAndView mav = galeriaController.listarObras();

        // validación
        assertThat(mav.getViewName(), equalToIgnoringCase("galeria"));
        assertThat((List<Obra>) mav.getModel().get("obras"), hasSize(2));
        verify(obraServiceMock, times(1)).listarObras();
    }
}
