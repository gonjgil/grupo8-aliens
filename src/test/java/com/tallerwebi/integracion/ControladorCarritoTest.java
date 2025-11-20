package com.tallerwebi.integracion;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioMail;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.integracion.config.CloudinaryTestConfig;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.integracion.config.TestConfigPaymentClient;
import com.tallerwebi.presentacion.ControladorCarrito;
import com.tallerwebi.presentacion.dto.ItemCarritoDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class, CloudinaryTestConfig.class, TestConfigPaymentClient.class})
public class ControladorCarritoTest {

    private MockMvc mockMvc;
    private ControladorCarrito controladorCarrito;
    private ServicioCarrito servicioCarritoMock;
    private ServicioMail servicioMailMock;

    @BeforeEach
    public void setUp() {
        this.servicioCarritoMock = mock(ServicioCarrito.class);
        this.servicioMailMock = mock(ServicioMail.class);

        this.controladorCarrito = new ControladorCarrito(servicioCarritoMock, servicioMailMock);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(controladorCarrito)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void verCarrito_debeRetornarCarritoViewConUsuarioLogueado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("usuario@test.com");

        List<ItemCarritoDto> items = new ArrayList<>();
        when(servicioCarritoMock.obtenerItems(usuario)).thenReturn(items);
        when(servicioCarritoMock.calcularPrecioTotalCarrito(usuario)).thenReturn(0.0);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(get("/carrito")
                .session(session))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv, notNullValue());
        assertThat(mv.getViewName(), equalTo("carrito"));
    }

    @Test
    public void verCarrito_debeRedirectALoginSinUsuarioLogueado() throws Exception {
        MockHttpSession session = new MockHttpSession();

        MvcResult result = mockMvc.perform(get("/carrito")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void verCarrito_debeIncluirDatosDelUsuarioEnModelo() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("usuario@test.com");

        List<ItemCarritoDto> items = new ArrayList<>();
        when(servicioCarritoMock.obtenerItems(usuario)).thenReturn(items);
        when(servicioCarritoMock.calcularPrecioTotalCarrito(usuario)).thenReturn(100.0);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(get("/carrito")
                .session(session))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv, notNullValue());
        assertThat(mv.getModel().get("usuario"), notNullValue());
        assertThat(mv.getModel().get("items"), notNullValue());
        assertThat(mv.getModel().get("total"), notNullValue());
    }

    @Test
    public void aumentarCantidad_debeRedirectACarritoConFormatoValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(servicioCarritoMock.agregarObraAlCarrito(usuario, 1L, com.tallerwebi.dominio.enums.Formato.ORIGINAL)).thenReturn(true);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(post("/carrito/aumentar/1")
                .param("formato", "ORIGINAL")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/carrito"));
    }

    @Test
    public void aumentarCantidad_debeRedirectAObraConParametroRedirect() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(servicioCarritoMock.agregarObraAlCarrito(usuario, 1L, com.tallerwebi.dominio.enums.Formato.DIGITAL)).thenReturn(true);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(post("/carrito/aumentar/1")
                .param("formato", "DIGITAL")
                .param("redirect", "obra")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/obra/1"));
    }

    @Test
    public void disminuirCantidad_debeRedirectACarritoConFormatoValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(post("/carrito/disminuir/1")
                .param("formato", "ORIGINAL")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/carrito"));
    }

    @Test
    public void disminuirCantidad_debeRedirectALoginSinUsuario() throws Exception {
        MockHttpSession session = new MockHttpSession();

        MvcResult result = mockMvc.perform(post("/carrito/disminuir/1")
                .param("formato", "ORIGINAL")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void eliminarDelCarrito_debeRedirectACarritoConFormatoValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(post("/carrito/eliminar/1")
                .param("formato", "ORIGINAL")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/carrito"));
    }

    @Test
    public void eliminarDelCarrito_debeRedirectALoginSinUsuario() throws Exception {
        MockHttpSession session = new MockHttpSession();

        MvcResult result = mockMvc.perform(post("/carrito/eliminar/1")
                .param("formato", "ORIGINAL")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void obtenerContadorCarrito_debeRetornarJSONConCount() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        List<ItemCarritoDto> items = new ArrayList<>();
        when(servicioCarritoMock.obtenerItems(usuario)).thenReturn(items);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(get("/carrito/contador")
                .session(session))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content, notNullValue());
    }

    @Test
    public void obtenerContadorCarrito_debeRetornarCeroSinUsuario() throws Exception {
        MockHttpSession session = new MockHttpSession();

        MvcResult result = mockMvc.perform(get("/carrito/contador")
                .session(session))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content, equalTo("{\"count\": 0}"));
    }

    @Test
    public void vaciarCarrito_debeRedirectACarritoConUsuarioValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(post("/carrito/vaciar")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/carrito"));
    }

    @Test
    public void vaciarCarrito_debeRedirectALoginSinUsuario() throws Exception {
        MockHttpSession session = new MockHttpSession();

        MvcResult result = mockMvc.perform(post("/carrito/vaciar")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void finalizarCompra_debeRedirectAGaleriaConCompraExitosa() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(post("/carrito/finalizar")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), notNullValue());
    }

    @Test
    public void finalizarCompra_debeRedirectALoginSinUsuario() throws Exception {
        MockHttpSession session = new MockHttpSession();

        MvcResult result = mockMvc.perform(post("/carrito/finalizar")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void finalizarCompra_debeRedirectACarritoSinDireccion() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(post("/carrito/finalizar")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/carrito"));
    }

    @Test
    public void agregarAlCarrito_debeRedirectAObraConFormatoValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(servicioCarritoMock.agregarObraAlCarrito(usuario, 1L, com.tallerwebi.dominio.enums.Formato.ORIGINAL)).thenReturn(true);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(post("/carrito/agregar")
                .param("id", "1")
                .param("formato", "ORIGINAL")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/obra/1"));
    }

}
