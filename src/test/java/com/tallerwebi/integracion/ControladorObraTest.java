package com.tallerwebi.integracion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.dominio.excepcion.NoExisteFormatoObra;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayObrasExistentes;
import com.tallerwebi.integracion.config.CloudinaryTestConfig;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.integracion.config.TestConfigPaymentClient;
import com.tallerwebi.presentacion.ControladorObra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.mock.web.MockMultipartFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class, CloudinaryTestConfig.class, TestConfigPaymentClient.class})
public class ControladorObraTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private ControladorObra controladorObra;

    private ServicioGaleria servicioGaleriaMock;
    private ServicioCarrito servicioCarritoMock;
    private ServicioPerfilArtista servicioPerfilArtistaMock;
    private ServicioCloudinary servicioCloudinaryMock;
    private ServicioFormatoObra servicioFormatoObraMock;
    private ServicioComentario servicioComentarioMock;

    private Usuario usuarioMock;


    @BeforeEach
    public void setup() {
        servicioGaleriaMock = mock(ServicioGaleria.class);
        servicioCarritoMock = mock(ServicioCarrito.class);
        servicioPerfilArtistaMock = mock(ServicioPerfilArtista.class);
        servicioCloudinaryMock = mock(ServicioCloudinary.class);
        servicioFormatoObraMock = mock(ServicioFormatoObra.class);
        servicioComentarioMock = mock(ServicioComentario.class);

        controladorObra = new ControladorObra(
                servicioGaleriaMock,
                servicioCarritoMock,
                servicioPerfilArtistaMock,
                servicioCloudinaryMock,
                servicioFormatoObraMock,
                servicioComentarioMock
        );

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(controladorObra)
                .setViewResolvers(viewResolver)
                .build();

        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setEmail("test@correo.com");



        when(servicioComentarioMock.obtenerComentariosDeObra(anyLong())).thenReturn(Collections.emptyList());
    }

    // ====================== GET /obra/{id} ======================
    @Test
    public void queUnUsuarioNoLoggeadoPuedaVerUnaObraExistente() throws Exception {
        Obra obra = new Obra();
        obra.setId(10L);
        obra.setTitulo("Mi obra");
        when(servicioGaleriaMock.obtenerPorId(10L)).thenReturn(obra);
        when(servicioFormatoObraMock.obtenerFormatosFaltantesPorObra(10L)).thenReturn(Collections.emptyList());
        when(servicioCarritoMock.obtenerCantidadDeItemPorId(null, obra)).thenReturn(0);

        MvcResult result = mockMvc.perform(get("/obra/10"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("obra"));
        assertThat(mv.getModel().get("usuario"), nullValue());
        assertThat(mv.getModel().get("obra"), notNullValue());
    }

    @Test
    public void queUnUsuarioLoggeadoPuedaVerUnaObraPropia() throws Exception {
        Obra obra = new Obra();
        obra.setId(20L);
        Artista artista = new Artista();
        artista.setId(2L);
        obra.setArtista(artista);

        when(servicioGaleriaMock.obtenerPorId(20L)).thenReturn(obra);
        when(servicioPerfilArtistaMock.obtenerArtistaPorUsuario(usuarioMock)).thenReturn(artista);
        when(servicioFormatoObraMock.obtenerFormatosFaltantesPorObra(20L)).thenReturn(Collections.emptyList());
        when(servicioCarritoMock.obtenerCantidadDeItemPorId(usuarioMock, obra)).thenReturn(1);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("usuarioLogueado", usuarioMock);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        MvcResult result = mockMvc.perform(get("/obra/20").sessionAttr("usuarioLogueado", usuarioMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("obra"));
        assertThat(mv.getModel().get("usuario"), notNullValue());
        assertThat(mv.getModel().get("esArtistaDuenio"), equalTo(true));
    }

    @Test
    public void queAlIntentarAccederAUnaObraInexistenteSeRedirijaAGaleria() throws Exception {
        when(servicioGaleriaMock.obtenerPorId(anyLong())).thenThrow(new NoHayObrasExistentes());

        MvcResult result = mockMvc.perform(get("/obra/99"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/galeria"));
        assertThat(mv.getModel().get("error"), notNullValue());
    }

    // ====================== GET /obra/nueva ======================
    @Test
    public void queUnUsuarioArtistaPuedaCrearUnaObra() throws Exception {
        Artista artista = new Artista();
        when(servicioPerfilArtistaMock.obtenerArtistaPorUsuario(usuarioMock)).thenReturn(artista);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("usuarioLogueado", usuarioMock);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        MvcResult result = mockMvc.perform(get("/obra/nueva").sessionAttr("usuarioLogueado", usuarioMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("nueva_obra"));
        assertThat(mv.getModel().get("artista"), notNullValue());
        assertThat(mv.getModel().get("categorias"), notNullValue());
    }

    @Test
    public void queUnUsuarioNoArtistaNoPuedaCrearUnaObraYRedirijaAGaleria() throws Exception {
        when(servicioPerfilArtistaMock.obtenerArtistaPorUsuario(usuarioMock))
                .thenThrow(new NoExisteArtista());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("usuarioLogueado", usuarioMock);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        MvcResult result = mockMvc.perform(get("/obra/nueva").sessionAttr("usuarioLogueado", usuarioMock))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/galeria"));
        assertThat(mv.getModel().get("error"), equalTo("Debes ser un artista registrado para agregar una obra."));
    }

    // ====================== POST /obra/crear ======================
    @Test
    public void debeCrearLaObra() throws Exception {
        Artista artista = new Artista();
        artista.setId(1L);
        Obra obraCreada = new Obra();
        obraCreada.setId(100L);

        when(servicioPerfilArtistaMock.obtenerArtistaPorUsuario(usuarioMock)).thenReturn(artista);
        when(servicioCloudinaryMock.subirImagen(any(), any())).thenReturn("url_imagen");
        when(servicioGaleriaMock.guardar(any(), eq(artista), eq("url_imagen"))).thenReturn(obraCreada);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("usuarioLogueado", usuarioMock);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        MockMultipartFile file = new MockMultipartFile(
                "file_obra",               // 👈 debe coincidir con el @RequestParam del controlador
                "imagen.jpg",
                "image/jpeg",
                "contenido".getBytes()
        );

        MvcResult result = mockMvc.perform(
                        multipart("/obra/crear")
                                .file(file)
                                .param("titulo", "Mi Obra")
                                .sessionAttr("usuarioLogueado", usuarioMock)
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), equalTo("/obra/100"));
    }

    @Test
    public void noDebePoderCrearLaObraElUsuarioNoArtista() throws Exception {
        when(servicioPerfilArtistaMock.obtenerArtistaPorUsuario(usuarioMock))
                .thenThrow(new NoExisteArtista());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("usuarioLogueado", usuarioMock);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        MockMultipartFile file = new MockMultipartFile(
                "file_obra",
                "imagen.jpg",
                "image/jpeg",
                "contenido".getBytes()
        );

        MvcResult result = mockMvc.perform(
                        multipart("/obra/crear")
                                .file(file)
                                .param("titulo", "Mi Obra")
                                .sessionAttr("usuarioLogueado", usuarioMock)
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), equalTo("/galeria"));
    }

    // ====================== GET /obra/{id}/editar ======================
    @Test
    public void queUnUsuarioArtistaDuenioPuedaEditarAtributosDeLaObra() throws Exception {
        Obra obra = new Obra();
        obra.setId(10L);
        Artista artista = new Artista();
        artista.setId(1L);
        obra.setArtista(artista);

        when(servicioGaleriaMock.obtenerPorId(10L)).thenReturn(obra);
        when(servicioPerfilArtistaMock.obtenerArtistaPorUsuario(usuarioMock)).thenReturn(artista);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("usuarioLogueado", usuarioMock);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        MvcResult result = mockMvc.perform(get("/obra/10/editar").sessionAttr("usuarioLogueado", usuarioMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("editar_obra"));
    }

    @Test
    public void queUnArtistaNoDuenioNoPuedaEditarDatosDeUnaObra() throws Exception {
        Obra obra = new Obra();
        obra.setId(10L);
        Artista artistaObra = new Artista();
        artistaObra.setId(2L); // Diferente del usuario
        obra.setArtista(artistaObra);

        Artista artistaUsuario = new Artista();
        artistaUsuario.setId(1L);

        when(servicioGaleriaMock.obtenerPorId(10L)).thenReturn(obra);
        when(servicioPerfilArtistaMock.obtenerArtistaPorUsuario(usuarioMock)).thenReturn(artistaUsuario);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("usuarioLogueado", usuarioMock);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        MvcResult result = mockMvc.perform(get("/obra/10/editar").sessionAttr("usuarioLogueado", usuarioMock))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/galeria"));
        assertThat(mv.getModel().get("error"), equalTo("No tienes permiso para editar esta obra."));
    }

    // ====================== POST /obra/{id}/actualizar ======================
    @Test
    public void debeActualizarDatosDeLaObra() throws Exception {
        doNothing().when(servicioGaleriaMock).actualizarObra(anyLong(), any(), anyList(), any());

        MvcResult result = mockMvc.perform(
                        post("/obra/10/actualizar")
                                .param("titulo", "Nuevo titulo")
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), equalTo("/obra/10"));
    }

    // ====================== POST /obra/{obraId}/agregar-formato ======================
    @Test
    public void debeAgregarUnFormatoALaObra() throws Exception {
        Obra obra = new Obra();
        obra.setId(5L);
        when(servicioGaleriaMock.obtenerPorId(5L)).thenReturn(obra);
        when(servicioFormatoObraMock.crearFormato(anyLong(), any(), anyDouble(), anyInt()))
                .thenReturn(new FormatoObra(obra, Formato.ORIGINAL, 100.0, 10));

        MvcResult result = mockMvc.perform(
                        post("/obra/5/agregar-formato")
                                .param("formato", "ORIGINAL")
                                .param("precio", "100")
                                .param("stock", "10")
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), equalTo("/obra/5"));
    }

    @Test
    public void queAlIntentarAgregarFormatoAUnaObraInexistenteSeRedirijaAGaleria() throws Exception {
        when(servicioGaleriaMock.obtenerPorId(anyLong())).thenThrow(new NoExisteLaObra());

        MvcResult result = mockMvc.perform(
                        post("/obra/5/agregar-formato")
                                .param("formato", "ORIGINAL")
                                .param("precio", "100")
                                .param("stock", "10")
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), equalTo("/galeria"));
    }

    // ====================== POST /obra/{obraId}/eliminar-formato ======================
    @Test
    public void debeEliminarUnFormatoDeObra() throws Exception {
        Obra obra = new Obra();
        obra.setId(5L);
        when(servicioGaleriaMock.obtenerPorId(5L)).thenReturn(obra);
        doNothing().when(servicioFormatoObraMock).eliminarFormato(anyLong());

        MvcResult result = mockMvc.perform(
                        post("/obra/5/eliminar-formato")
                                .param("formatoId", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), equalTo("/obra/5"));
    }

    @Test
    public void queAlIntentarEliminarUnFormatoInexistenteDeObraSeRedirijaAGaleria() throws Exception {
        Obra obra = new Obra();
        obra.setId(5L);
        when(servicioGaleriaMock.obtenerPorId(5L)).thenReturn(obra);
        doThrow(new NoExisteFormatoObra()).when(servicioFormatoObraMock).eliminarFormato(anyLong());

        MvcResult result = mockMvc.perform(
                        post("/obra/5/eliminar-formato")
                                .param("formatoId", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), equalTo("/galeria"));
    }

    // ====================== POST /obra/{obraId}/actualizar-formato ======================
    @Test
    public void debeActualizarDatosDelFormato() throws Exception {
        doNothing().when(servicioFormatoObraMock).actualizarFormatoObra(anyLong(), anyDouble(), anyInt());

        MvcResult result = mockMvc.perform(
                        post("/obra/5/actualizar-formato")
                                .param("formatoId", "1")
                                .param("nuevoPrecio", "150")
                                .param("nuevoStock", "20")
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), equalTo("/obra/5"));
    }

    @Test
    public void queAlIntentarActualizarDatosDeUnFormatoInexistenteSeRedirijaAGaleria() throws Exception {
        doThrow(new NoExisteFormatoObra()).when(servicioFormatoObraMock).actualizarFormatoObra(anyLong(), anyDouble(), anyInt());

        MvcResult result = mockMvc.perform(
                        post("/obra/5/actualizar-formato")
                                .param("formatoId", "1")
                                .param("nuevoPrecio", "150")
                                .param("nuevoStock", "20")
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), equalTo("/galeria"));
    }

    @Test
    public void queCargueLosComentariosDeLaObra() throws Exception {
        Obra obra = new Obra();
        obra.setId(1L);

        Comentario comentario = new Comentario();
        comentario.setContenido("Muy buena obra");
        List<Comentario> comentarios = List.of(comentario);

        when(servicioGaleriaMock.obtenerPorId(1L)).thenReturn(obra);
        when(servicioComentarioMock.obtenerComentariosDeObra(1L)).thenReturn(comentarios);

        MvcResult result = mockMvc.perform(get("/obra/1"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getModel().get("comentarios"), notNullValue());
        assertThat(((List<?>) mv.getModel().get("comentarios")).size(), equalTo(1));
    }

}
