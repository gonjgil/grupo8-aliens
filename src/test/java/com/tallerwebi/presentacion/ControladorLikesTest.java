package com.tallerwebi.presentacion;

import java.util.Map;

import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioLike;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.presentacion.dto.ObraDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControladorLikesTest {


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
    public void queUnUsuarioLoggeadoPuedaDarLikeAUnaObra() throws NoExisteLaObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioLike servicioLike = mock(ServicioLike.class);
        Long id = 1L;

        Obra obra = new Obra();
        obra.setId(id);

        ObraDto obraDto = new ObraDto(obra);
        when(servicioGaleria.obtenerPorId(id)).thenReturn(obra);
        when(servicioLike.toggleLike(usuario, id)).thenAnswer(invocation -> {
            obra.darLike(usuario);
            return true; // true = se dio like
        });

        ControladorLikes controladorLikes = new ControladorLikes(servicioGaleria, servicioLike);

        ResponseEntity<?> response = controladorLikes.toggleLike(id, request);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertThat(body.get("liked"), is(true));
        assertThat(body.get("obraId"), is(id));
        assertThat(body.get("likesCount"), is(1));
    }

    @Test
    public void queUnUsuarioAnonimoNoPuedaDarLikeAUnaObra() throws NoExisteLaObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioLike servicioLike = mock(ServicioLike.class);
        Long id = 1L;

        when(this.request.getSession().getAttribute("usuarioLogueado")).thenReturn(null);

        ControladorLikes controladorLikes = new ControladorLikes(servicioGaleria, servicioLike);

        ResponseEntity<?> response = controladorLikes.toggleLike(id, request);

        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertThat(body.get("error"), is("Debe estar logueado para dar o quitar like."));
    }

    @Test
    public void queUnUsuarioLoggeadoPuedaQuitarLikeAUnaObra() throws NoExisteLaObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioLike servicioLike = mock(ServicioLike.class);
        Long id = 1L;

        Obra obra = new Obra();
        obra.setId(id);
        obra.darLike(usuario); // el usuario ya había dado like antes

        ObraDto obraDto = new ObraDto(obra);
        when(servicioGaleria.obtenerPorId(id)).thenReturn(obra);
        when(servicioLike.toggleLike(usuario, id)).thenAnswer(invocation -> {
            obra.quitarLike(usuario);
            return false; // false = se quitó el like
        });

        ControladorLikes controladorLikes = new ControladorLikes(servicioGaleria, servicioLike);

        ResponseEntity<?> response = controladorLikes.toggleLike(id, request);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertThat(body.get("liked"), is(false));
        assertThat(body.get("obraId"), is(id));
        assertThat(body.get("likesCount"), is(0));
    }
}

