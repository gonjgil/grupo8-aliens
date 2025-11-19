package com.tallerwebi.integracion;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioCompraHecha;
import com.tallerwebi.dominio.ServicioMail;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.integracion.config.CloudinaryTestConfig;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.integracion.config.TestConfigPaymentClient;
import com.tallerwebi.presentacion.ControladorPagos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class, CloudinaryTestConfig.class, TestConfigPaymentClient.class})
public class ControladorPagosTest {

    private MockMvc mockMvc;
    private ControladorPagos controladorPagos;
    private ServicioCompraHecha servicioCompraHechaMock;
    private ServicioCarrito servicioCarritoMock;
    private ServicioMail servicioMailMock;

    @BeforeEach
    public void setUp() {
        this.servicioCompraHechaMock = mock(ServicioCompraHecha.class);
        this.servicioCarritoMock = mock(ServicioCarrito.class);
        this.servicioMailMock = mock(ServicioMail.class);

        this.controladorPagos = new ControladorPagos(servicioCompraHechaMock, servicioCarritoMock, servicioMailMock);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(controladorPagos)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void pagoExitoso_debeRedirectAHistorialCuandoCompraExitosa() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Carrito carrito = mock(Carrito.class);
        CompraHecha compra = mock(CompraHecha.class);
        when(compra.getId()).thenReturn(1L);

        when(servicioCarritoMock.obtenerCarritoConItems(usuario)).thenReturn(carrito);
        when(servicioCompraHechaMock.crearResumenCompraAPartirDeCarrito(carrito, 123L)).thenReturn(compra);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogueado", usuario);

        MvcResult result = mockMvc.perform(get("/api/pagos/success")
                .param("payment_id", "123")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/compras/historial"));
    }

    @Test
    public void pagoExitoso_debeRedirectALoginSiNoHayUsuarioLogueado() throws Exception {
        MockHttpSession session = new MockHttpSession();

        MvcResult result = mockMvc.perform(get("/api/pagos/success")
                .param("payment_id", "123")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName(), equalTo("redirect:/login"));
    }

}
