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
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.servicioImpl.ServicioGaleriaImpl;
import com.tallerwebi.presentacion.dto.ObraDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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

import java.util.List;
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

//        assertThat(modelAndView.getViewName(), is(equalTo("redirect:/obra/" + obraGuardada.getId())));
//        assertThat(modelAndView.getModel().get("usuario"), is(equalTo(usuario)));
//
//        verify(servicioPerfilArtista).obtenerArtistaPorUsuario(usuario);
//        verify(servicioCloudinary).subirImagen(archivo, TipoImagen.OBRA);
//        verify(servicioGaleria).guardar(any(Obra.class), eq(artista), anyString());
    }

    @Test
    void queUnArtistaRegistradoPuedaEditarUnaObraPropia() {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria, servicioCarrito,
                servicioPerfilArtista, servicioCloudinary);

        Long idObra = 1L;

        Artista artista = new Artista();
        artista.setId(10L);

        Obra obra = new Obra();
        obra.setId(idObra);
        obra.setTitulo("Mi obra original");
        obra.setDescripcion("Descripción vieja");
        obra.setArtista(artista);

        when(servicioGaleria.obtenerPorId(idObra)).thenReturn(obra);
        when(servicioPerfilArtista.obtenerArtistaPorUsuario(usuario)).thenReturn(artista);

        ModelAndView mav = controladorObra.editarObra(idObra, request);

        assertThat("Debe devolver la vista de edición de la obra",
                mav.getViewName(), is("editar_obra"));

        assertThat("El modelo debe contener el DTO de la obra",
                mav.getModel().get("obra"), notNullValue());

        assertThat("El modelo debe contener las categorías",
                mav.getModel().get("categorias"), notNullValue());
        verify(servicioGaleria, times(1)).obtenerPorId(idObra);
        verify(servicioPerfilArtista, times(1)).obtenerArtistaPorUsuario(usuario);
    }

    @Test
    void queUnUsuarioNoArtistaNoPuedaEditarUnaObra() {
        ServicioGaleria servicioGaleria = mock(ServicioGaleria.class);
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ServicioPerfilArtista servicioPerfilArtista = mock(ServicioPerfilArtista.class);
        ServicioCloudinary servicioCloudinary = mock(ServicioCloudinary.class);

        ControladorObra controladorObra = new ControladorObra(servicioGaleria, servicioCarrito,
                servicioPerfilArtista, servicioCloudinary);

        Long idObra = 1L;

        Obra obra = new Obra();
        Artista artistaPropietario = new Artista();
        artistaPropietario.setId(10L); // Otro artista distinto
        obra.setId(idObra);
        obra.setArtista(artistaPropietario);

        when(servicioGaleria.obtenerPorId(idObra)).thenReturn(obra);
        when(servicioPerfilArtista.obtenerArtistaPorUsuario(usuario)).thenReturn(null); // No es artista

        ModelAndView mav = controladorObra.editarObra(idObra, request);

        assertThat("El usuario no artista debería ser redirigido a galería",
                mav.getViewName(), is("redirect:/galeria"));

        assertThat("El modelo debe contener mensaje de error",
                mav.getModel().get("error"), is("No tienes permiso para editar esta obra."));

        verify(servicioGaleria, times(1)).obtenerPorId(idObra);
        verify(servicioPerfilArtista, times(1)).obtenerArtistaPorUsuario(usuario);
    }

    @Test
    void queSeActualicenTodosLosCamposDeLaObraCorrectamente() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioGaleria servicioGaleria = new ServicioGaleriaImpl(repositorioObra);

        Long idObra = 1L;
        Obra obraExistente = new Obra();
        obraExistente.setId(idObra);
        obraExistente.setTitulo("Viejo título");
        obraExistente.setDescripcion("Vieja descripción");
        obraExistente.setImagenUrl("vieja-url.jpg");
        obraExistente.setCategorias(Set.of(Categoria.PINTURA));

        when(repositorioObra.obtenerPorId(idObra)).thenReturn(obraExistente);

        ObraDto dto = new ObraDto();
        dto.setTitulo("Nuevo título");
        dto.setDescripcion("Nueva descripción");

        List<String> categoriasSeleccionadas = List.of("PINTURA", "ESCULTURA", "ARTE_MIXTO");
        String nuevaUrl = "nueva-url.jpg";

        // ACT
        servicioGaleria.actualizarObra(idObra, dto, categoriasSeleccionadas, nuevaUrl);

        // ASSERT con Hamcrest
        assertThat(obraExistente.getTitulo(), is("Nuevo título"));
        assertThat(obraExistente.getDescripcion(), is("Nueva descripción"));
        assertThat(obraExistente.getImagenUrl(), is("nueva-url.jpg"));
        assertThat(obraExistente.getCategorias(), containsInAnyOrder(
                Categoria.PINTURA, Categoria.ESCULTURA, Categoria.ARTE_MIXTO
        ));

        verify(repositorioObra, times(1)).guardar(obraExistente);
    }

    @Test
    void queActualizarObraSinCambiarImagenMantieneLaImagenExistente() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioGaleria servicioGaleria = new ServicioGaleriaImpl(repositorioObra);

        Long idObra = 1L;
        Obra obraExistente = new Obra();
        obraExistente.setId(idObra);
        obraExistente.setTitulo("Viejo título");
        obraExistente.setDescripcion("Vieja descripción");
        obraExistente.setImagenUrl("vieja-url.jpg");
        obraExistente.setCategorias(Set.of(Categoria.PINTURA));

        when(repositorioObra.obtenerPorId(idObra)).thenReturn(obraExistente);

        ObraDto dto = new ObraDto();
        dto.setTitulo("Nuevo título");
        dto.setDescripcion("Nueva descripción");

        List<String> categoriasSeleccionadas = List.of("PINTURA", "ESCULTURA");

        // ACT
        servicioGaleria.actualizarObra(idObra, dto, categoriasSeleccionadas, null); // sin nueva imagen

        // ASSERT con Hamcrest
        assertThat(obraExistente.getTitulo(), is("Nuevo título"));
        assertThat(obraExistente.getDescripcion(), is("Nueva descripción"));
        assertThat(obraExistente.getImagenUrl(), is("vieja-url.jpg")); // se mantiene la imagen
        assertThat(obraExistente.getCategorias(), containsInAnyOrder(
                Categoria.PINTURA, Categoria.ESCULTURA
        ));

        verify(repositorioObra, times(1)).guardar(obraExistente);
    }
}
