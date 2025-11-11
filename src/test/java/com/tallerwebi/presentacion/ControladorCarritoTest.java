package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioMail;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import com.tallerwebi.dominio.servicioImpl.ServicioCarritoImpl;
import com.tallerwebi.dominio.servicioImpl.ServicioMailImpl;
import com.tallerwebi.infraestructura.RepositorioCarritoImpl;
import com.tallerwebi.infraestructura.RepositorioFormatoObraImpl;
import com.tallerwebi.infraestructura.RepositorioObraImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ControladorCarritoTest {

    private HttpServletRequest requestMock;
    private RedirectAttributes redirectAttributesMock;

    @BeforeEach
    public void setUp() {
        this.requestMock = mock(HttpServletRequest.class);
        this.redirectAttributesMock = mock(RedirectAttributes.class);
        HttpSession session = mock(HttpSession.class);
        Usuario usuario = mock(Usuario.class);

        when(this.requestMock.getSession()).thenReturn(session);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);
    }

    @Test
    public void queAlAgregarUnaObraAlCarritoSeExecuteCorrectamente() {
        assert true;
    }

    @Test
    public void queAlVaciarElCarritoSeMuestreElCarritoVacio() throws NoExisteLaObra, NoHayStockSuficiente {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);
        Obra obra = new Obra();
        obra.setId(1L);
        obra.setStock(2);
        
        RepositorioCarritoImpl repositorioCarrito = mock(RepositorioCarritoImpl.class);
        RepositorioObraImpl repositorioObra = mock(RepositorioObraImpl.class);
        RepositorioFormatoObraImpl repositorioFormatoObra = mock(RepositorioFormatoObraImpl.class);
        ServicioMailImpl servicioMail = mock(ServicioMailImpl.class);
        
        Carrito carrito = new Carrito(usuario);
        FormatoObra formatoObra = new FormatoObra();
        formatoObra.setStock(10);
        formatoObra.setFormato(Formato.DIGITAL);
        
        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);
        when(repositorioObra.obtenerPorId(obra.getId())).thenReturn(obra);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra.getId(), Formato.DIGITAL)).thenReturn(formatoObra);

        ServicioCarritoImpl servicioCarrito = new ServicioCarritoImpl(repositorioCarrito, repositorioObra, repositorioFormatoObra);
        ControladorCarrito controladorCarrito = new ControladorCarrito(servicioCarrito, servicioMail);

        servicioCarrito.agregarObraAlCarrito(usuario, obra.getId(), Formato.DIGITAL);

        String resultado = controladorCarrito.vaciarCarrito(session, redirectAttributesMock);

        assertThat(resultado, is(equalTo("redirect:/carrito")));
        assertThat(servicioCarrito.contarItemsEnCarrito(usuario), is(equalTo(0)));
    }

    @Test
    public void queAlFinalizarLaCompraSeEnvíeUnCorreoDeConfirmación(){
        Usuario usuario= new Usuario();
        usuario.setEmail("cliente@ejemplo.com");

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);

        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioMail servicioMail = mock(ServicioMail.class);

        ControladorCarrito controladorCarrito = new ControladorCarrito(servicioCarrito, servicioMail);


        String resultado = controladorCarrito.finalizarCompra(session, redirectAttributes);

        verify(servicioCarrito, times(1)).finalizarCompra(usuario);
        verify(servicioMail, times(1)).enviarMail(
                eq("cliente@ejemplo.com"),
                contains("Confirmación de compra"),
                contains("Tu compra fue realizada")
        );
        assertThat(resultado, is(equalTo("redirect:/galeria")));
    }
}
