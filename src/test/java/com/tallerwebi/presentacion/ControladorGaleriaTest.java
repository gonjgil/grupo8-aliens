package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.excepcion.NoHayObrasExistentes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

public class ControladorGaleriaTest {

    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        this.request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class); // mock de la sesión
        Usuario usuario = mock(Usuario.class);

        when(this.request.getSession()).thenReturn(session); // la request devuelve la sesión
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario); // la sesión devuelve el usuario
    }

    @Test
    public void mostrarGaleria_deberiaRetornarListaVaciaSiNoHayObras() throws NoHayObrasExistentes {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);

        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria);
        doThrow(NoHayObrasExistentes.class).when(servicioGaleria).obtener();

        ModelAndView modelAndView = controladorGaleria.mostrarGaleria(this.request);

        assertThat(modelAndView.getViewName(), is(equalToIgnoringCase("galeria")));
        List<ObraDto> obrasDtoObtenidas = (List<ObraDto>) modelAndView.getModel().get("obras");
        assertThat(obrasDtoObtenidas.size(), is(equalTo(0)));
        assertThat(modelAndView.getModel().get("error").toString(), is(equalToIgnoringCase("No hay obras.")));
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
        ModelAndView modelAndView = controladorGaleria.mostrarGaleria(this.request);

        // verificacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("galeria"));
        List<ObraDto> obrasDtoObtenidas = (List<ObraDto>) modelAndView.getModel().get("obras");
        assertThat(obrasDtoObtenidas.size(), is(equalTo(4)));
        assertThat(modelAndView.getModel().get("exito").toString(), is(equalToIgnoringCase("Hay obras.")));
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

        ModelAndView modelAndView = controladorGaleria.mostrarGaleria(this.request);

        assertThat(modelAndView.getViewName(), is(equalTo("galeria")));
        assertThat(modelAndView.getModel().get("randomObras"), is(equalTo(randomObras)));
        assertThat(modelAndView.getModel().get("autorObras"), is(equalTo(autorObras)));
        assertThat(modelAndView.getModel().get("temaObras"), is(equalTo(temaObras)));
    }

}
