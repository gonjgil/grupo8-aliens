package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Artista;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorPerfilArtistaTest {
    private ControladorPerfilArtista controladorPerfilArtista;
    private ServicioPerfilArtista servicioPerfilArtistaMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {
        servicioPerfilArtistaMock = mock(ServicioPerfilArtista.class);
        controladorPerfilArtista = new ControladorPerfilArtista(servicioPerfilArtistaMock);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
    }

    @Test
    public void deberiaMostrarPerfilDeArtistaCuandoExiste() {
        // Preparación
        Long idArtista = 1L;
        PerfilArtistaDTO artistaMock = new PerfilArtistaDTO();
        when(servicioPerfilArtistaMock.obtenerPerfilArtista(idArtista)).thenReturn(artistaMock);

        // Ejecución
        ModelAndView modelAndView = controladorPerfilArtista.verPerfilArtista(idArtista, requestMock);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("perfil-artista"));
        assertThat(modelAndView.getModel().get("mostrarArtista"), equalTo(artistaMock));

    }

    @Test
    public void deberiaMostrarPaginaDeErrorCuandoArtistaNoExiste(){
        // Preparación
        Long idArtista = 2L;
        when(servicioPerfilArtistaMock.obtenerPerfilArtista(idArtista)).thenThrow(new NoExisteArtista());

        // Ejecución
        ModelAndView modelAndView = controladorPerfilArtista.verPerfilArtista(idArtista, requestMock);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("PerfilNoExiste"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("perfil no encontrado"));
    }
    @Test
    public void deberiaCrearArtistaYRedirigirASuPerfil() {
        // Preparación
        PerfilArtistaDTO dto = new PerfilArtistaDTO();
        dto.setNombre("Artista");
        dto.setBiografia("Bio");
        dto.setUrlFacebook("facebook.com/artista");

        Artista artistaCreado = new Artista("Artista", "Bio", null);
        artistaCreado.setId(10L); // Simula el ID

        //Cuando el metodo crearPerfilArtista del mock servicioPerfilArtistaMock sea llamado con CUALQUIER objeto de tipo PerfilArtistaDTO como argumento, entonces devuelve artistaCreado."
        when(servicioPerfilArtistaMock.crearPerfilArtista(any(PerfilArtistaDTO.class))).thenReturn(artistaCreado);

        // Ejecución
        String redirectUrl = controladorPerfilArtista.crearArtista(dto);

        // Validación
        assertThat(redirectUrl, is(equalTo("redirect:/perfilArtista/10"))); // Verifica la URL de redirección

    }

}
