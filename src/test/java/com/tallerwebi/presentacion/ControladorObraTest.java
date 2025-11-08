package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioCloudinary;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.enums.TipoImagen;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.presentacion.dto.ObraDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

        Obra obra = mock(Obra.class);
        obra.setId(1L);
        obra.setTitulo("Obra A");
        obra.setAutor("Autor A");
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

//        assertThat(modelAndView.getViewName(), is(equalTo("redirect:/obra/" + obraGuardada.getId())));
//        assertThat(modelAndView.getModel().get("usuario"), is(equalTo(usuario)));
//
//        verify(servicioPerfilArtista).obtenerArtistaPorUsuario(usuario);
//        verify(servicioCloudinary).subirImagen(archivo, TipoImagen.OBRA);
//        verify(servicioGaleria).guardar(any(Obra.class), eq(artista), anyString());
    }


}
