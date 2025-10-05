package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.ServicioGaleria;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorObraTest {

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
    public void verObra_deberiaMostrarVistaConDatosDeLaObra() throws Exception {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        Obra obra = mock(Obra.class);
        ObraDto obraDto = new ObraDto(obra);
        obraDto.setTitulo("Obra A");
        obraDto.setAutor("Autor A");
        obraDto.setDescripcion("Lorem Ipsum");
        
        when(servicioGaleria.obtenerPorId(1L)).thenReturn(obraDto);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria);

        ModelAndView modelAndView = controladorObra.verObra(1L, request);

        assertThat(modelAndView.getViewName(), is(equalTo("obra")));
        assertThat(modelAndView.getModel().get("obra"), is(equalTo(obraDto)));
    }
}
