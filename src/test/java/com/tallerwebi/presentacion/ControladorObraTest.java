package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioLike;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.presentacion.dto.ObraDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorObraTest {

    private HttpServletRequest request;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        this.request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        this.usuario = mock(Usuario.class);

        when(this.request.getSession()).thenReturn(session);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);
    }

    @Test
    public void verObra_deberiaMostrarVistaConDatosDeLaObra() throws Exception {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioLike servicioLike = mock(ServicioLike.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);

        Obra obra = mock(Obra.class);
        obra.setId(1L);
        obra.setTitulo("Obra A");
        obra.setAutor("Autor A");
        obra.setDescripcion("Lorem Ipsum");

        ObraDto obraDto = new ObraDto(obra);
        when(servicioGaleria.obtenerPorId(1L)).thenReturn(obra);
        // when(servicioCarrito.contarItemsEnCarrito(usuario)).thenReturn(2);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria, servicioLike, servicioCarrito, servicioPerfilArtista);

        ModelAndView modelAndView = controladorObra.verObra(1L, request);


        assertThat(modelAndView.getViewName(), is(equalTo("obra")));
        assertThat(modelAndView.getModel().get("obra"), is(equalTo(obraDto)));
        // assertThat(modelAndView.getModel().get("cantidadItems"), is(equalTo(2)));
    }

    @Test
    public void queAlIntentarAccederAUnaObraNoValidaSeRedirijaAVistaGaleria() throws NoExisteLaObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioLike servicioLike = mock(ServicioLike.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        when(servicioGaleria.obtenerPorId(999L)).thenThrow(new NoExisteLaObra());
        

        ControladorObra controladorObra = new ControladorObra(servicioGaleria, servicioLike, servicioCarrito, servicioPerfilArtista);
        ModelAndView modelAndView = controladorObra.verObra(999L, request);

        assertThat(modelAndView.getViewName(), is(equalTo("redirect:/galeria")));
    }

}
