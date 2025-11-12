package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.enums.TipoImagen;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.dominio.excepcion.NoExisteFormatoObra;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.presentacion.dto.ObraDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

public class ControladorObraTest {

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
    public void verObra_deberiaMostrarVistaConDatosDeLaObra() throws Exception {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        Artista artista = new Artista("Autor A", "Biografia", "http://example.com/autorA.jpg");
        Obra obra = mock(Obra.class);
        obra.setId(1L);
        obra.setTitulo("Obra A");
        obra.setArtista(artista);
        obra.setDescripcion("Lorem Ipsum");

        ObraDto obraDto = new ObraDto(obra);
        when(servicioGaleria.obtenerPorId(1L)).thenReturn(obra);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria, servicioCarrito, servicioPerfilArtista, servicioCloudinary, servicioFormatoObra);

        ModelAndView modelAndView = controladorObra.verObra(1L, request);

        assertThat(modelAndView.getViewName(), is(equalTo("obra")));
        assertThat(modelAndView.getModel().get("obra"), is(equalTo(obraDto)));
    }

    @Test
    public void queAlIntentarAccederAUnaObraNoValidaSeRedirijaAVistaGaleria() throws NoExisteLaObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        when(servicioGaleria.obtenerPorId(999L)).thenThrow(new NoExisteLaObra());

        ControladorObra controladorObra = new ControladorObra(servicioGaleria, servicioCarrito, servicioPerfilArtista, servicioCloudinary, servicioFormatoObra);
        ModelAndView modelAndView = controladorObra.verObra(999L, request);

        assertThat(modelAndView.getViewName(), is(equalTo("redirect:/galeria")));
    }

    @Test
    public void queUnArtistaRegistradoPuedaVerElFormularioDeNuevaObra() throws NoExisteArtista {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary,
                servicioFormatoObra
        );

        Artista artista = new Artista("Frida Kahlo", "Pintora mexicana", "http://example.com/frida.jpg");
        when(servicioPerfilArtista.obtenerArtistaPorUsuario(usuario)).thenReturn(artista);

        ModelAndView modelAndView = controladorObra.nuevaObra(request);

        assertThat(modelAndView.getViewName(), is(equalTo("nueva_obra")));
        assertThat(modelAndView.getModel().get("categorias"), is(equalTo(Categoria.values())));
        assertThat(modelAndView.getModel().get("artista"), is(notNullValue()));
    }

    @Test
    public void queUnUsuarioNoArtistaNoPuedaVerElFormularioDeNuevaObra() {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        Usuario usuarioNoArtista = mock(Usuario.class);

        when(servicioPerfilArtista.obtenerArtistaPorUsuario(usuarioNoArtista)).thenThrow(new NoExisteArtista());
        when(request.getSession().getAttribute("usuarioLogueado")).thenReturn(usuarioNoArtista);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria, servicioCarrito, servicioPerfilArtista, servicioCloudinary, servicioFormatoObra);

        ModelAndView modelAndView = controladorObra.nuevaObra(request);

        assertThat(modelAndView.getViewName(), is(equalTo("redirect:/galeria")));
    }

    @Test
    public void queUnArtistaRegistradoPuedaCrearUnaNuevaObra() throws NoExisteArtista {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary,
                servicioFormatoObra
        );

        ObraDto obraDto = mock(ObraDto.class);
        when(obraDto.toObra()).thenReturn(new Obra());
        MultipartFile archivo = mock(MultipartFile.class);

        Artista artista = new Artista("Frida Kahlo", "Pintora mexicana", "http://example.com/frida.jpg");
        when(servicioPerfilArtista.obtenerArtistaPorUsuario(usuario)).thenReturn(artista);

        when(servicioCloudinary.subirImagen(archivo, TipoImagen.OBRA)).thenReturn("http://example.com/imagen.jpg");
        Obra obraGuardada = new Obra();
        obraGuardada.setId(1L);
        when(servicioGaleria.guardar(any(Obra.class), eq(artista), anyString())).thenReturn(obraGuardada);

        String ret = controladorObra.crearObra(obraDto, archivo, request);

        assertThat(ret, is(equalTo("redirect:/obra/" + obraGuardada.getId())));
    }

    @Test
    public void queSePuedaCrearUnNuevoFormatoParaUnaObra() throws NoExisteLaObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary,
                servicioFormatoObra
        );

        Long obraId = 1L;
        Formato formato = Formato.DIGITAL;
        Double precio = 1500.0;
        Integer stock = 10;

        Obra obra = new Obra("Título", "imagen.jpg", "Descripción", null, null);
        obra.setId(obraId);
        FormatoObra formatoObra = new FormatoObra(obra, formato, precio, stock);

        when(servicioFormatoObra.crearFormato(obraId, formato, precio, stock))
                .thenReturn(formatoObra);

        String resultado = controladorObra.agregarFormato(obraId, formato, precio, stock);

        verify(servicioFormatoObra, times(1))
                .crearFormato(obraId, formato, precio, stock);

        assertThat(resultado, is("redirect:/obra/" + obraId));
        assertThat(formatoObra.getFormato(), is(formato));
        assertThat(formatoObra.getPrecio(), is(precio));
        assertThat(formatoObra.getStock(), is(stock));
    }

    @Test
    public void queAlIntentarCrearFormatoDeObraInexistenteSeRedirijaAGaleria() throws NoExisteLaObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary,
                servicioFormatoObra
        );

        Long obraId = 99L;
        Formato formato = Formato.ORIGINAL;

        doThrow(new NoExisteLaObra())
                .when(servicioFormatoObra)
                .crearFormato(eq(obraId), eq(formato), anyDouble(), anyInt());

        String resultado = controladorObra.agregarFormato(obraId, formato, 1000.0, 5);

        verify(servicioFormatoObra, times(1))
                .crearFormato(eq(obraId), eq(formato), anyDouble(), anyInt());

        assertThat(resultado, is("redirect:/galeria"));
    }

    @Test
    public void queSePuedaEliminarUnFormatoDeUnaObra() throws NoExisteFormatoObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary,
                servicioFormatoObra
        );

        Long formatoId = 1L;
        Long obraId = 1L;

        String resultado = controladorObra.eliminarFormato(obraId, formatoId);

        verify(servicioFormatoObra, times(1)).eliminarFormato(formatoId);
        assertThat(resultado, is("redirect:/obra/" + obraId));
    }

    @Test
    public void queAlEliminarFormatoInexistenteSeRedirijaAGaleria() throws NoExisteFormatoObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary,
                servicioFormatoObra
        );

        Long formatoId = 99L;
        Long obraId = 1L;
        doThrow(new NoExisteFormatoObra()).when(servicioFormatoObra).eliminarFormato(formatoId);

        String resultado = controladorObra.eliminarFormato(obraId, formatoId);

        verify(servicioFormatoObra, times(1)).eliminarFormato(formatoId);
        assertThat(resultado, is("redirect:/galeria"));
    }

    @Test
    public void queSePuedaActualizarElPrecioDeUnFormato() throws NoExisteFormatoObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary,
                servicioFormatoObra
        );

        Long obraId = 1L;
        Long formatoId = 1L;
        Double nuevoPrecio = 2500.0;

        String resultado = controladorObra.actualizarPrecio(obraId, formatoId, nuevoPrecio);

        verify(servicioFormatoObra, times(1)).actualizarPrecio(formatoId, nuevoPrecio);
        assertThat(resultado, is("redirect:/obra/" + obraId));
    }

    @Test
    public void queAlActualizarPrecioDeFormatoInexistenteSeRedirijaAGaleria() throws NoExisteFormatoObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary,
                servicioFormatoObra
        );

        Long obraId = 1L;
        Long formatoId = 99L;
        Double nuevoPrecio = 2500.0;

        doThrow(new NoExisteFormatoObra()).when(servicioFormatoObra).actualizarPrecio(formatoId, nuevoPrecio);

        String resultado = controladorObra.actualizarPrecio(obraId, formatoId, nuevoPrecio);

        verify(servicioFormatoObra, times(1)).actualizarPrecio(formatoId, nuevoPrecio);
        assertThat(resultado, is("redirect:/galeria"));
    }

    @Test
    public void queSePuedaActualizarElStockDeUnFormato() throws NoExisteFormatoObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary,
                servicioFormatoObra
        );

        Long obraId = 1L;
        Long formatoId = 1L;
        Integer nuevoStock = 15;

        String resultado = controladorObra.actualizarStock(obraId, formatoId, nuevoStock);

        verify(servicioFormatoObra, times(1)).actualizarStock(formatoId, nuevoStock);
        assertThat(resultado, is("redirect:/obra/" + obraId));
    }

    @Test
    public void queAlActualizarStockDeFormatoInexistenteSeRedirijaAGaleria() throws NoExisteFormatoObra {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);
        ServicioFormatoObra servicioFormatoObra = mock(ServicioFormatoObra.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary,
                servicioFormatoObra
        );

        Long obraId = 1L;
        Long formatoId = 99L;
        Integer nuevoStock = 15;

        doThrow(new NoExisteFormatoObra()).when(servicioFormatoObra).actualizarStock(formatoId, nuevoStock);

        String resultado = controladorObra.actualizarStock(obraId, formatoId, nuevoStock);

        verify(servicioFormatoObra, times(1)).actualizarStock(formatoId, nuevoStock);
        assertThat(resultado, is("redirect:/galeria"));
    }


}
