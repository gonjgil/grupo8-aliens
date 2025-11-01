package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;

import com.tallerwebi.presentacion.dto.ObraDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.excepcion.NoHayObrasExistentes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

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
        // Arrange
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        Usuario usuario = mock(Usuario.class);
        when(request.getSession()).thenReturn(mock(javax.servlet.http.HttpSession.class));
        when(request.getSession().getAttribute("usuarioLogueado")).thenReturn(usuario);

        doThrow(NoHayObrasExistentes.class).when(servicioGaleria).obtenerObrasParaUsuario(usuario);

        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria);

        // Act
        ModelAndView modelAndView = controladorGaleria.mostrarGaleria(this.request);

        // Assert
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("galeria"));
        List<ObraDto> obrasDtoObtenidas = (List<ObraDto>) modelAndView.getModel().get("obrasSpotlight");
        assertThat(obrasDtoObtenidas, is(empty()));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("No hay obras."));
    }

    @Test
    public void siHay4obrasDeberiaRetornar4obras() throws NoHayObrasExistentes {
        // Arrange
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        Usuario usuario = mock(Usuario.class);
        when(request.getSession()).thenReturn(mock(javax.servlet.http.HttpSession.class));
        when(request.getSession().getAttribute("usuarioLogueado")).thenReturn(usuario);

        List<Obra> obras = List.of(mock(Obra.class), mock(Obra.class), mock(Obra.class), mock(Obra.class));
        when(servicioGaleria.obtenerObrasParaUsuario(usuario)).thenReturn(obras);

        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria);

        // Act
        ModelAndView modelAndView = controladorGaleria.mostrarGaleria(this.request);

        // Assert
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("galeria"));
        List<ObraDto> obrasDtoObtenidas = (List<ObraDto>) modelAndView.getModel().get("obrasSpotlight");
        assertThat(obrasDtoObtenidas, hasSize(4));
        assertThat(modelAndView.getModel().get("exito").toString(), equalToIgnoringCase("Hay obras."));
    }

//    @Test
//    public void alMostrarseLaGaleriaDeberianMostrarseTresListasDiferentes() {
//        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
//
//        List<Obra> obras = new ArrayList<>();
//        obras.add(mock(Obra.class));
//
//        List<Obra> randomObras = new ArrayList<>();
//        List<Obra> autorObras = new ArrayList<>();
//        List<Obra> temaObras = new ArrayList<>();
//
//        when(servicioGaleria.obtener()).thenReturn(obras);
//        when(servicioGaleria.ordenarRandom()).thenReturn(randomObras);
//        when(servicioGaleria.obtenerPorAutor(Mockito.anyString())).thenReturn(autorObras);
//        when(servicioGaleria.obtenerPorCategoria(Mockito.any(Categoria.class))).thenReturn(temaObras);
//
//        ControladorGaleria controladorGaleria = new ControladorGaleria(servicioGaleria);
//
//        ModelAndView modelAndView = controladorGaleria.mostrarGaleria(this.request);
//
//
//        assertThat(modelAndView.getViewName(), is(equalTo("galeria")));
//        assertThat(modelAndView.getModel().get("randomObras"), is(equalTo(randomObras)));
//        assertThat(modelAndView.getModel().get("autorObras"), is(equalTo(autorObras)));
//        assertThat(modelAndView.getModel().get("temaObras"), is(equalTo(temaObras)));
//        // assertThat(modelAndView.getModel().get("cantidadItems"), is(equalTo(1)));
//    }

}
