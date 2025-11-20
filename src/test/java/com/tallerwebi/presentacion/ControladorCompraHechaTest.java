package com.tallerwebi.presentacion;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.ServicioCompraHecha;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.presentacion.dto.CompraHechaDto;
import com.tallerwebi.presentacion.dto.ItemCompraDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorCompraHechaTest {

    private ControladorCompraHecha controladorCompraHecha;
    private ServicioCompraHecha servicioCompraHecha;
    private HttpSession session;

    @BeforeEach
    public void setUp(){
        this.servicioCompraHecha = mock(ServicioCompraHecha.class);
        this.controladorCompraHecha = new ControladorCompraHecha(servicioCompraHecha);
        this.session = mock(HttpSession.class);
    }

    @Test
    public void debriaDevolverOkCuandoSeDevuelvaElDetalleCompraCorrectamente() throws MPException, MPApiException {
        //preparacio
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Usuario");
        Artista artista = new Artista();
        artista.setId(1L);
        artista.setNombre("Artista");

        Obra obra = new Obra();
        obra.setId(1L);
        obra.setArtista(artista);

        ItemCompra item = new ItemCompra();
        item.setObra(obra);
        item.setCantidad(2);
        item.setPrecioUnitario(1000.0);

        CompraHecha compra = new CompraHecha();
        compra.setUsuario(usuario);
        List<ItemCompra> items = new ArrayList<>();
        items.add(item);
        compra.setItems(items);
        compra.setPrecioFinal(2000.0);
        compra.setFechaYHora(LocalDateTime.now());

        when(session.getAttribute("usuarioLogueado")).thenReturn(usuario);
        when(servicioCompraHecha.obtenerCompraPorId(1L)).thenReturn(compra);

        //ejecucion
        ResponseEntity<CompraHechaDto> response = controladorCompraHecha.obtenerDetalleCompra(1L, session);

        //validacion
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getNombreUsuario(), is(usuario.getNombre()));
        assertThat(response.getBody().getItems().size(), is(1));
        assertThat(response.getBody().getItems().get(0).getObraId(), is(obra.getId()));

    }

@Test
    public void debriaDevolverUnauthorizedCuandoElUsuarioNoEsteLogueado() throws MPException, MPApiException {
        //preparacion
        when(session.getAttribute("usuarioLogueado")).thenReturn(null);

        //ejecucion
        ResponseEntity<CompraHechaDto> response = controladorCompraHecha.obtenerDetalleCompra(1L, session);

        //validacion
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    public void debriaDevolverNotFoundCuandoLaCompraNoPertenescaAlUsuarioLogueado() throws MPException, MPApiException {
        //preparacion
        Usuario usuarioLogueado = new Usuario();
        usuarioLogueado.setId(1L);

        Usuario otroUsuario = new Usuario();
        otroUsuario.setId(2L);

        CompraHecha compra = new CompraHecha();
        compra.setUsuario(otroUsuario);

        when(session.getAttribute("usuarioLogueado")).thenReturn(usuarioLogueado);
        when(servicioCompraHecha.obtenerCompraPorId(1L)).thenReturn(compra);

        //ejecucion
        ResponseEntity<CompraHechaDto> response = controladorCompraHecha.obtenerDetalleCompra(1L, session);

        //validacion
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));
    }


    @Test
    public void deberiaMostrarComprasDelUsuarioCorrectamente(){
        Usuario usuarioLogueado = new Usuario();
        usuarioLogueado.setId(1L);

        Artista artista = new Artista();
        artista.setId(1L);
        artista.setNombre("Artista");

        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setTitulo("obra1");
        obra1.setArtista(artista);

        Obra obra2 = new Obra();
        obra2.setId(2L);
        obra2.setTitulo("obra2");
        obra2.setArtista(artista);

        ItemCompra item1 = new ItemCompra();
        item1.setObra(obra1);
        item1.setCantidad(1);
        item1.setPrecioUnitario(500.0);
        ItemCompra item2 = new ItemCompra();
        item2.setObra(obra2);
        item2.setCantidad(2);
        item2.setPrecioUnitario(750.0);

        CompraHecha compra = new CompraHecha();
        compra.setUsuario(usuarioLogueado);
        List<ItemCompra> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        item1.setCompra(compra);
        item2.setCompra(compra);
        compra.setItems(items);
        compra.setPrecioFinal(2000.0);
        compra.setFechaYHora(LocalDateTime.now());

        List<CompraHecha> compras = new ArrayList<>();
        compras.add(compra);

        when(session.getAttribute("usuarioLogueado")).thenReturn(usuarioLogueado);
        when(servicioCompraHecha.obtenerComprasPorUsuario(usuarioLogueado)).thenReturn(compras);

        ModelAndView modelAndView = controladorCompraHecha.verMisCompras(session);

        assertThat(modelAndView.getViewName(), is("compras_historial"));
        List<CompraHechaDto> comprasDto = (List<CompraHechaDto>) modelAndView.getModel().get("compras");
        assertThat(comprasDto.size(), is(1));
        assertThat(comprasDto.get(0).getItems().size(), is(2));

    }

    @GetMapping("/historial")
    public ModelAndView verMisCompras(HttpSession session) {
        ModelMap modelo = new ModelMap();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        List<CompraHecha> compras = servicioCompraHecha.obtenerComprasPorUsuario(usuario);
        List<CompraHechaDto> comprasDto = new ArrayList<>();

        for (CompraHecha compra : compras) {
            List<ItemCompraDto> itemsDto = new ArrayList<>();
            for (ItemCompra item : compra.getItems()) {
                itemsDto.add(new ItemCompraDto(item));
            }
            comprasDto.add(new CompraHechaDto(compra, itemsDto));
        }

        modelo.put("compras", comprasDto.isEmpty() ? Collections.emptyList() : comprasDto);
        modelo.put("usuario", usuario);

        return new ModelAndView("compras_historial", modelo);
    }

//    @GetMapping("/error")
//    public ModelAndView mostrarError() {
//        return new ModelAndView("compras_error");
//    }
//

}
