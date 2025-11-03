package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.ServicioCloudinary;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.presentacion.dto.PerfilArtistaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
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
    private ServicioCloudinary servicioCloudinaryMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {
        servicioPerfilArtistaMock = mock(ServicioPerfilArtista.class);
        servicioCloudinaryMock = mock(ServicioCloudinary.class);
        controladorPerfilArtista = new ControladorPerfilArtista(servicioPerfilArtistaMock, servicioCloudinaryMock);
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
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("perfil_artista"));
        assertThat(modelAndView.getModel().get("mostrarArtista"), equalTo(artistaMock));

    }

    @Test
    public void deberiaRedirigirAGaleriaCuandoArtistaNoExiste(){
        // Preparación
        Long idArtista = 2L;
        when(servicioPerfilArtistaMock.obtenerPerfilArtista(idArtista)).thenThrow(new NoExisteArtista());

        // Ejecución
        ModelAndView modelAndView = controladorPerfilArtista.verPerfilArtista(idArtista, requestMock);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/galeria"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("perfil no encontrado"));
    }
    @Test
    public void deberiaCrearArtistaYRedirigirASuPerfil() {
        // Preparación

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setEmail("usuario@prueba.com");

        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuarioMock);

        PerfilArtistaDTO dto = new PerfilArtistaDTO();
        dto.setNombre("Artista");
        dto.setBiografia("Bio");
        dto.setUrlFacebook("facebook.com/artista");

        Artista artistaCreado = new Artista("Artista", "Bio", null);
        artistaCreado.setId(10L); // Simula el ID
        artistaCreado.setUsuario(usuarioMock);

        MultipartFile archivoMock = mock(MultipartFile.class);
        when(archivoMock.isEmpty()).thenReturn(false);
        when(servicioCloudinaryMock.subirImagen(any(MultipartFile.class)))
                .thenReturn("https://cloudinary.com/foto.jpg");
        when(servicioPerfilArtistaMock.crearPerfilArtista(any(PerfilArtistaDTO.class), any(Usuario.class)))
                .thenReturn(artistaCreado);

        //Cuando el metodo crearPerfilArtista del mock servicioPerfilArtistaMock sea llamado con CUALQUIER objeto de tipo PerfilArtistaDTO como argumento, entonces devuelve artistaCreado."
        when(servicioPerfilArtistaMock.crearPerfilArtista(any(PerfilArtistaDTO.class), any(Usuario.class))).thenReturn(artistaCreado);

        // Ejecución
        String redirectUrl = controladorPerfilArtista.crearArtista(dto, archivoMock, requestMock);

        // Validación
        assertThat(redirectUrl, is(equalTo("redirect:/perfilArtista/ver/10"))); // Verifica la URL de redirección

    }

}
