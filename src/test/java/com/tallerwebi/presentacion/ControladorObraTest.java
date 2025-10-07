package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.UsuarioAnonimoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioCarrito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashSet;
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
        HttpSession session = mock(HttpSession.class);
        Usuario usuario = mock(Usuario.class);

        when(this.request.getSession()).thenReturn(session);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);
    }

    @Test
    public void verObra_deberiaMostrarVistaConDatosDeLaObra() throws Exception {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);

        Usuario usuario = (Usuario) this.request.getSession().getAttribute("usuarioLogueado");

        Obra obra = mock(Obra.class);
        obra.setId(1L);
        obra.setTitulo("Obra A");
        obra.setAutor("Autor A");
        obra.setDescripcion("Lorem Ipsum");

        when(servicioGaleria.obtenerPorId(1L)).thenReturn(obra);
        // when(servicioCarrito.contarItemsEnCarrito(usuario)).thenReturn(2);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria, servicioCarrito);
        ObraDto obraDto = new ObraDto(obra);

        ModelAndView modelAndView = controladorObra.verObra(1L, request);


        assertThat(modelAndView.getViewName(), is(equalTo("obra")));
        assertThat(modelAndView.getModel().get("obra"), is(equalTo(obraDto)));
        // assertThat(modelAndView.getModel().get("cantidadItems"), is(equalTo(2)));
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
    public void queUnUsuarioLoggeadoPuedaDarLikeAUnaObra() throws NoExisteLaObra, UsuarioAnonimoException {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        Long id = 1L;
        Obra obra = new Obra();
        obra.setId(id);
        obra.setTitulo("Obra A");
        obra.setAutor("Autor A");
        obra.setDescripcion("Lorem Ipsum");
        obra.setUsuariosQueDieronLike(new HashSet<>());

        when(servicioGaleria.obtenerPorId(id)).thenReturn(obra);

        // Simula que toggle agrega el like si no estaba
        doAnswer(invoc -> {
            obra.getUsuariosQueDieronLike().add(usuario);
            return null;
        }).when(servicioGaleria).toggleLike(id, this.usuario);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria);
        ModelAndView modelAndView = controladorObra.toggleLike(id, request);
        ObraDto obraDto = new ObraDto(obra);
        ObraDto obraDtoEnModel = (ObraDto) modelAndView.getModel().get("obra");

        assertThat(modelAndView.getViewName(), is(equalTo("redirect:/obra/" + id)));
        assertThat(modelAndView.getModel().get("obra"), is(equalTo(obraDto)));
        assertThat(obraDtoEnModel.getUsuariosQueDieronLike().size(), is(equalTo(1)));
    }

    @Test
    public void queUnUsuarioAnonimoNoPuedaDarLikeAUnaObra() throws NoExisteLaObra, UsuarioAnonimoException {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        Long id = 1L;
        Obra obra = new Obra();
        obra.setId(id);
        obra.setTitulo("Obra A");
        obra.setAutor("Autor A");
        obra.setDescripcion("Lorem Ipsum");

        when(servicioGaleria.obtenerPorId(id)).thenReturn(obra);
        doThrow(new UsuarioAnonimoException()).when(servicioGaleria).toggleLike(id, null);
        when(this.request.getSession().getAttribute("usuarioLogueado")).thenReturn(null);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria);
        ModelAndView modelAndView = controladorObra.toggleLike(id, request);

        assertThat(modelAndView.getViewName(), is(equalTo("redirect:/obra/" + id)));
        assertThat(modelAndView.getModel().get("error"), is(equalTo("Debe estar logueado para dar/quitar like.")));
    }

    @Test
    public void queUnUsuarioLoggeadoPuedaQuitarLikeAUnaObra() throws NoExisteLaObra, UsuarioAnonimoException {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        Long id = 1L;
        Obra obra = new Obra();
        obra.setId(id);
        obra.setTitulo("Obra A");
        obra.setAutor("Autor A");
        obra.setDescripcion("Lorem Ipsum");
        obra.setUsuariosQueDieronLike(new HashSet<>(Set.of(usuario))); // ya tenía el like

        when(servicioGaleria.obtenerPorId(id)).thenReturn(obra);

        doAnswer(invoc -> {
            obra.getUsuariosQueDieronLike().remove(usuario);
            return null;
        }).when(servicioGaleria).toggleLike(id, this.usuario);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria);
        ModelAndView modelAndView = controladorObra.toggleLike(id, request);
        ObraDto obraDto = new ObraDto(obra);
        ObraDto obraDtoEnModel = (ObraDto) modelAndView.getModel().get("obra");

        assertThat(modelAndView.getViewName(), is(equalTo("redirect:/obra/" + id)));
        assertThat(modelAndView.getModel().get("obra"), is(equalTo(obraDto)));
        assertThat(obraDtoEnModel.getUsuariosQueDieronLike().size(), is(equalTo(0)));
    }

}
