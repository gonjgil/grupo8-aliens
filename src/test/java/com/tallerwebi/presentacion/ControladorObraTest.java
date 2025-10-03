package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.UsuarioAnonimoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.ServicioGaleria;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ControladorObraTest {

    private HttpServletRequest request;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        this.request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class); // mock de la sesión
        this.usuario = mock(Usuario.class);

        when(this.request.getSession()).thenReturn(session); // la request devuelve la sesión
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario); // la sesión devuelve el usuario
    }

    @Test
    public void verObra_deberiaMostrarVistaConDatosDeLaObra() throws Exception {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        Obra obra = mock(Obra.class);
        obra.setId(1L);
        obra.setTitulo("Obra A");
        obra.setAutor("Autor A");
        obra.setDescripcion("Lorem Ipsum");

        when(servicioGaleria.obtenerPorId(1L)).thenReturn(obra);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria);
        ObraDto obraDto = new ObraDto(obra);

        ModelAndView modelAndView = controladorObra.verObra(1L, request);

        assertThat(modelAndView.getViewName(), is(equalTo("obra")));
        assertThat(modelAndView.getModel().get("obra"), is(equalTo(obraDto)));
    }

    @Test
    public void queAlIntentarAccederAUnaObraNoValidaSeRedirijaAVistaGaleria() throws NoExisteLaObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        when(servicioGaleria.obtenerPorId(999L)).thenThrow(new NoExisteLaObra());

        ControladorObra controladorObra = new ControladorObra(servicioGaleria);
        ModelAndView modelAndView = controladorObra.verObra(999L, request);

        assertThat(modelAndView.getViewName(), is(equalTo("redirect:/galeria_alt")));
    }

    @Test
    public void queUnUsuarioLoggeadoPuedaDarLikeAUnaObra() throws NoExisteLaObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        Obra obra = new Obra();
        obra.setId(1L);
        obra.setTitulo("Obra A");
        obra.setAutor("Autor A");
        obra.setDescripcion("Lorem Ipsum");

        when(servicioGaleria.obtenerPorId(1L)).thenReturn(obra);

        doAnswer(invoc -> {
            obra.setUsuariosQueDieronLike(Set.of(usuario));
            return null;
        }).when(servicioGaleria).darLike(1L, this.usuario);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria);
        ModelAndView modelAndView = controladorObra.darLike(1L, request);
        ObraDto obraDto = new ObraDto(obra);
        ObraDto obraDtoEnModel = (ObraDto) modelAndView.getModel().get("obra");

        assertThat(modelAndView.getViewName(), is(equalTo("obra")));
        assertThat(modelAndView.getModel().get("obra"), is(equalTo(obraDto)));
        assertThat(obraDtoEnModel.getUsuariosQueDieronLike().size(), is(equalTo(1)));
    }

    @Test
    public void queUnUsuarioAnonimoNoPuedaDarLikeAUnaObra() throws NoExisteLaObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        Obra obra = mock(Obra.class);
        obra.setId(1L);
        obra.setTitulo("Obra A");
        obra.setAutor("Autor A");
        obra.setDescripcion("Lorem Ipsum");

        when(servicioGaleria.obtenerPorId(1L)).thenReturn(obra);
        doThrow(new UsuarioAnonimoException()).when(servicioGaleria).darLike(1L, null);
        when(this.request.getSession().getAttribute("usuarioLogueado")).thenReturn(null);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria);
        ObraDto obraDto = new ObraDto(obra);
        assertThrows(UsuarioAnonimoException.class, () -> controladorObra.darLike(1L, request));
        assertThat(obraDto.getUsuariosQueDieronLike().size(), is(equalTo(0)));
    }
}
