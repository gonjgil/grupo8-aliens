package com.tallerwebi.integracion;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.integracion.config.CloudinaryTestConfig;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.NoHayObrasExistentes;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioPerfilArtista;

import com.tallerwebi.presentacion.ControladorGaleria;
import com.tallerwebi.presentacion.dto.ObraDto;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.transaction.Transactional;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class, CloudinaryTestConfig.class})
public class ControladorGaleriaTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private ControladorGaleria controladorGaleria;

    private ServicioGaleria servicioGaleriaMock;
    private ServicioPerfilArtista servicioPerfilArtistaMock;

    private Usuario usuarioMock;

    @BeforeEach
    public void setup() {
        servicioGaleriaMock = mock(ServicioGaleria.class);
        servicioPerfilArtistaMock = mock(ServicioPerfilArtista.class);

        controladorGaleria = new ControladorGaleria(servicioGaleriaMock, servicioPerfilArtistaMock);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/thymeleaf/");
        viewResolver.setSuffix(".html"); // o .html si usás Thymeleaf

        mockMvc = MockMvcBuilders.standaloneSetup(controladorGaleria)
                .setViewResolvers(viewResolver)
                .build();

        usuarioMock = new Usuario();
        usuarioMock.setEmail("test@correo.com");
        usuarioMock.setPassword("123");
    }

    @Test
    public void debeMostrarGaleriaConObrasRandomCuandoNoHayUsuario() throws Exception {
        List<Obra> obras = Arrays.asList(new Obra(), new Obra());
        when(servicioGaleriaMock.ordenarRandom()).thenReturn(obras);

        MvcResult result = mockMvc.perform(get("/galeria"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv, notNullValue());
        assertThat(mv.getViewName(), equalToIgnoringCase("galeria"));
        assertThat(mv.getModel().get("obrasSpotlight"), instanceOf(List.class));
        assertThat(mv.getModel().get("exito"), equalTo("Hay obras."));
    }

    @Test
    public void debeMostrarGaleriaConObrasParaUsuarioLogueado() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("usuarioLogueado", usuarioMock);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Artista artista = new Artista();
        when(servicioPerfilArtistaMock.obtenerArtistaPorUsuario(usuarioMock)).thenReturn(artista);

        Obra obra = new Obra();
        obra.setId(10L);
        obra.setTitulo("Puesta de Sol");
        when(servicioGaleriaMock.obtenerObrasParaUsuario(usuarioMock))
                .thenReturn(Collections.singletonList(obra));

        MvcResult result = mockMvc.perform(get("/galeria").sessionAttr("usuarioLogueado", usuarioMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv, notNullValue());
        assertThat(mv.getViewName(), equalTo("galeria"));
        assertThat(mv.getModel().get("obrasSpotlight"), notNullValue());
        assertThat(mv.getModel().get("exito"), equalTo("Hay obras."));
    }

    @Test
    public void debeMostrarErrorCuandoNoHayObras_SinUsuario() throws Exception {
        when(servicioGaleriaMock.ordenarRandom()).thenThrow(new NoHayObrasExistentes());

        MvcResult result = mockMvc.perform(get("/galeria"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getModel().get("error"), equalTo("No hay obras."));
        assertThat((List<?>) mv.getModel().get("obrasSpotlight"), empty());
    }

    @Test
    @Transactional
    public void debeMostrarErrorCuandoNoHayObras_ConUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@correo.com");
        usuario.setPassword("123");
        SessionFactory sessionFactory = wac.getBean(SessionFactory.class);
        sessionFactory.getCurrentSession().save(usuario);

        when(servicioGaleriaMock.obtenerObrasParaUsuario(any()))
                .thenThrow(new NoHayObrasExistentes());

        MvcResult result = mockMvc.perform(get("/galeria")
                        .sessionAttr("usuarioLogueado", usuario))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getModel().get("error"), equalTo("No hay obras."));
        assertThat((List<?>) mv.getModel().get("obrasSpotlight"), empty());
    }
}
