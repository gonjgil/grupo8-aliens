package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioCloudinary;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.enums.TipoImagen;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.servicioImpl.ServicioGaleriaImpl;
import com.tallerwebi.presentacion.dto.ObraDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hsqldb.lib.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

        Artista artista = new Artista("Autor A", "Biografia", "http://example.com/autorA.jpg");
        Obra obra = mock(Obra.class);
        obra.setId(1L);
        obra.setTitulo("Obra A");
        obra.setArtista(artista);
        obra.setDescripcion("Lorem Ipsum");

        ObraDto obraDto = new ObraDto(obra);
        when(servicioGaleria.obtenerPorId(1L)).thenReturn(obra);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria, servicioCarrito, servicioPerfilArtista, servicioCloudinary);

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

        when(servicioGaleria.obtenerPorId(999L)).thenThrow(new NoExisteLaObra());

        ControladorObra controladorObra = new ControladorObra(servicioGaleria, servicioCarrito, servicioPerfilArtista, servicioCloudinary);
        ModelAndView modelAndView = controladorObra.verObra(999L, request);

        assertThat(modelAndView.getViewName(), is(equalTo("redirect:/galeria")));
    }

    @Test
    public void queUnArtistaRegistradoPuedaVerElFormularioDeNuevaObra() throws NoExisteArtista {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary
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

        Usuario usuarioNoArtista = mock(Usuario.class);

        when(servicioPerfilArtista.obtenerArtistaPorUsuario(usuarioNoArtista)).thenThrow(new NoExisteArtista());
        when(request.getSession().getAttribute("usuarioLogueado")).thenReturn(usuarioNoArtista);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria, servicioCarrito, servicioPerfilArtista, servicioCloudinary);

        ModelAndView modelAndView = controladorObra.nuevaObra(request);

        assertThat(modelAndView.getViewName(), is(equalTo("redirect:/galeria")));
    }

    @Test
    public void queUnArtistaRegistradoPuedaCrearUnaNuevaObra() throws NoExisteArtista {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary
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
    public void queSePuedaGuardarUnNuevoFormatoParaUnaObra() {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);

        ControladorObra controladorObra = new ControladorObra(
                servicioGaleria,
                servicioCarrito,
                servicioPerfilArtista,
                servicioCloudinary
        );

        Long obraId = 1L;
        Formato formato = Formato.DIGITAL;
        Double precio = 1500.0;
        Integer stock = 10;

        String resultado = controladorObra.agregarFormato(obraId, formato, precio, stock);

        verify(servicioGaleria, times(1))
                .agregarFormatoObra(obraId, formato, precio, stock);

        assertThat(resultado, is("redirect:/obra/" + obraId));
    }


}
