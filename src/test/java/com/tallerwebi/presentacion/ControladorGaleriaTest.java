package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.presentacion.ObraDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioCarrito;
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
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

public class ControladorGaleriaTest {

    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        this.request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        Usuario usuario = mock(Usuario.class);

        when(this.request.getSession()).thenReturn(session);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);
    }

    @Test
    public void mostrarGaleria_deberiaRetornarListaVaciaSiNoHayObras() throws NoHayObrasExistentes {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        
        doThrow(NoHayObrasExistentes.class).when(servicioGaleria).obtener();
        when(servicioCarrito.contarItemsEnCarrito(any(Usuario.class))).thenReturn(0);
        
        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria, servicioCarrito);

        ModelAndView modelAndView = controladorGaleria.mostrarGaleria(this.request);


        assertThat(modelAndView.getViewName(), is(equalToIgnoringCase("galeria")));
        List<ObraDto> obrasDtoObtenidas = (List<ObraDto>) modelAndView.getModel().get("obras");
        assertThat(obrasDtoObtenidas.size(), is(equalTo(0)));
        assertThat(modelAndView.getModel().get("error").toString(), is(equalToIgnoringCase("No hay obras.")));
        assertThat(modelAndView.getModel().get("cantidadItems"), is(equalTo(0)));
    }

    @Test
    public void siHay4obrasDeberiaRetornar4obras() {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        
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
        when(servicioCarrito.contarItemsEnCarrito(any(Usuario.class))).thenReturn(2);

        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria, servicioCarrito);

        ModelAndView modelAndView = controladorGaleria.mostrarGaleria(this.request);


        assertThat(modelAndView.getViewName(), equalToIgnoringCase("galeria"));
        List<ObraDto> obrasDtoObtenidas = (List<ObraDto>) modelAndView.getModel().get("obras");
        assertThat(obrasDtoObtenidas.size(), is(equalTo(4)));
        assertThat(modelAndView.getModel().get("exito").toString(), is(equalToIgnoringCase("Hay obras.")));
        assertThat(modelAndView.getModel().get("cantidadItems"), is(equalTo(2)));
    }

    @Test
    public void alMostrarseLaGaleriaDeberianMostrarseTresListasDiferentes() {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        
        List<ObraDto> obrasDto = new ArrayList<>();
        ObraDto obra1 = mock(ObraDto.class);
        obrasDto.add(obra1);
        
        List<ObraDto> randomObras = new ArrayList<>();
        List<ObraDto> autorObras = new ArrayList<>();
        List<ObraDto> temaObras = new ArrayList<>();

        when(servicioGaleria.obtener()).thenReturn(obrasDto);
        when(servicioGaleria.ordenarRandom()).thenReturn(randomObras);
        when(servicioGaleria.obtenerPorAutor(Mockito.anyString())).thenReturn(autorObras);
        when(servicioGaleria.obtenerPorCategoria(Mockito.any(Categoria.class))).thenReturn(temaObras);
        when(servicioCarrito.contarItemsEnCarrito(any(Usuario.class))).thenReturn(1);

        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria, servicioCarrito);

        ModelAndView modelAndView = controladorGaleria.mostrarGaleria(this.request);


        assertThat(modelAndView.getViewName(), is(equalTo("galeria")));
        assertThat(modelAndView.getModel().get("randomObras"), is(equalTo(randomObras)));
        assertThat(modelAndView.getModel().get("autorObras"), is(equalTo(autorObras)));
        assertThat(modelAndView.getModel().get("temaObras"), is(equalTo(temaObras)));
        assertThat(modelAndView.getModel().get("cantidadItems"), is(equalTo(1)));
    }

}
