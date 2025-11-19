package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEstadistica;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.ServicioCloudinary;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.enums.TipoImagen;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.presentacion.dto.CategoriaEstadisticaDto;
import com.tallerwebi.presentacion.dto.ObraDto;
import com.tallerwebi.presentacion.dto.PerfilArtistaDTO;
import com.tallerwebi.presentacion.dto.ObraDtoWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorPerfilArtistaTest {
    private ControladorPerfilArtista controladorPerfilArtista;
    private ServicioPerfilArtista servicioPerfilArtistaMock;
    private ServicioCloudinary servicioCloudinaryMock;
    private ServicioEstadistica servicioEstadisticaMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {
        servicioPerfilArtistaMock = mock(ServicioPerfilArtista.class);
        servicioCloudinaryMock = mock(ServicioCloudinary.class);
        servicioEstadisticaMock = mock(ServicioEstadistica.class);
        controladorPerfilArtista = new ControladorPerfilArtista(servicioPerfilArtistaMock, servicioCloudinaryMock, servicioEstadisticaMock);
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
        when(servicioCloudinaryMock.subirImagen(any(MultipartFile.class), eq(TipoImagen.PERFIL_ARTISTA)))
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

    @Test
    public void queUnUsuarioNoPuedaEditarElPerfilDeOtroArtista() {
        // Preparación
        Long idArtista = 1L;
        PerfilArtistaDTO artistaMock = new PerfilArtistaDTO();
        artistaMock.setUsuarioId(2L); // El artista pertenece a otro usuario

        when(servicioPerfilArtistaMock.obtenerPerfilArtista(idArtista)).thenReturn(artistaMock);

        Usuario usuarioLogueado = new Usuario();
        usuarioLogueado.setId(3L); // Usuario logueado diferente
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuarioLogueado);

        // Ejecución
        ModelAndView modelAndView = controladorPerfilArtista.mostrarFormularioDeEdicion(idArtista, requestMock);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/galeria"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("No tienes permiso para editar este perfil."));
    }

    @Test
    public void deberiaRedirigirAGaleriaCuandoElArtistaNoExisteEnEstadisticas() {
        Long idArtista = 1L;
        when(servicioPerfilArtistaMock.obtenerPerfilArtista(idArtista))
                .thenThrow(new NoExisteArtista());

        ModelAndView modelAndView = controladorPerfilArtista.mostrarEstadisticas(idArtista, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/galeria"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("perfil no encontrado"));
    }

    @Test
    public void deberiaDenegarAccesoASiElUsuarioNoEsElDuenoDelPerfilEnEstadisticas() {
        Long idArtista = 1L;

        PerfilArtistaDTO artistaMock = new PerfilArtistaDTO();
        artistaMock.setUsuarioId(50L);
        when(servicioPerfilArtistaMock.obtenerPerfilArtista(idArtista)).thenReturn(artistaMock);

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(20L);
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorPerfilArtista.mostrarEstadisticas(idArtista, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/galeria"));
        assertThat(modelAndView.getModel().get("error").toString(),
                equalToIgnoringCase("Acceso denegado"));
    }

    @Test
    public void deberiaMostrarEstadisticasCuandoElUsuarioCoincideConElDuenoDelPerfil() {
        // Preparación
        Long idArtista = 1L;

        PerfilArtistaDTO artistaMock = mock(PerfilArtistaDTO.class);
        when(artistaMock.getUsuarioId()).thenReturn(10L);
        when(artistaMock.toArtista()).thenReturn(new Artista());

        when(servicioPerfilArtistaMock.obtenerPerfilArtista(idArtista))
                .thenReturn(artistaMock);

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(10L);
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuarioMock);

        // Datos mock de estadísticas
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Categoria categoria = Categoria.ABSTRACTO;

        Map<Obra, Long> masVendidas = Map.of(obra1, 6L);
        Map<Obra, Long> masLikeadas = Map.of(obra2, 17L);
        Map<Categoria, Long> categoriasVendidas = Map.of(categoria, 5L);
        Map<Categoria, Long> categoriasLikeadas = Map.of(categoria, 10L);

        List<Obra> trendVentas = List.of(obra1);
        List<Obra> trendLikes = List.of(obra2);

        when(servicioEstadisticaMock.obtenerMasVendidasArtista(any())).thenReturn(masVendidas);
        when(servicioEstadisticaMock.obtenerMasLikeadasArtista(any())).thenReturn(masLikeadas);
        when(servicioEstadisticaMock.obtenerTresCategoriasMasVendidasArtista(any())).thenReturn(categoriasVendidas);
        when(servicioEstadisticaMock.obtenerTresCategoriasMasLikeadasArtista(any())).thenReturn(categoriasLikeadas);
        when(servicioEstadisticaMock.obtenerTrendingVentasArtista(any())).thenReturn(trendVentas);
        when(servicioEstadisticaMock.obtenerTrendingLikesArtista(any())).thenReturn(trendLikes);

        // Ejecución
        ModelAndView mv = controladorPerfilArtista.mostrarEstadisticas(idArtista, requestMock);

        // Validación vista
        assertThat(mv.getViewName(), equalToIgnoringCase("estadisticas_artista"));

        // Validación modelo
        assertThat(mv.getModel().get("artista"), equalTo(artistaMock));

        // --- VALIDACIÓN DE ESTRUCTURA DE LOS DTOS ---

        List<?> masVendidasResult = (List<?>) mv.getModel().get("masVendidas");
        assertThat(masVendidasResult.size(), equalTo(1));
        assertThat(masVendidasResult.get(0), instanceOf(ObraDtoWrapper.class));

        List<?> masLikeadasResult = (List<?>) mv.getModel().get("masLikeadas");
        assertThat(masLikeadasResult.size(), equalTo(1));
        assertThat(masLikeadasResult.get(0), instanceOf(ObraDtoWrapper.class));

        List<?> catMasVendidasResult = (List<?>) mv.getModel().get("cat_masVendidas");
        assertThat(catMasVendidasResult.size(), equalTo(1));
        assertThat(catMasVendidasResult.get(0), instanceOf(CategoriaEstadisticaDto.class));

        List<?> catMasLikeadasResult = (List<?>) mv.getModel().get("cat_masLikeadas");
        assertThat(catMasLikeadasResult.size(), equalTo(1));
        assertThat(catMasLikeadasResult.get(0), instanceOf(CategoriaEstadisticaDto.class));

        List<?> trendVendidasResult = (List<?>) mv.getModel().get("trendVendidas");
        assertThat(trendVendidasResult.size(), equalTo(1));
        assertThat(trendVendidasResult.get(0), instanceOf(ObraDto.class));

        List<?> trendLikeadasResult = (List<?>) mv.getModel().get("trendLikeadas");
        assertThat(trendLikeadasResult.size(), equalTo(1));
        assertThat(trendLikeadasResult.get(0), instanceOf(ObraDto.class));
    }

}
