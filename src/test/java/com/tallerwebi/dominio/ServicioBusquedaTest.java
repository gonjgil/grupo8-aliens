package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioBusquedaTest {

    @Mock
    private RepositorioObra repositorioObra;
    @Mock
    private RepositorioArtista repositorioArtista;

    private ServicioBusquedaImpl servicioBusqueda;

    @BeforeEach
    public void init(){
        this.repositorioObra = mock(RepositorioObra.class);
        this.repositorioArtista = mock(RepositorioArtista.class);
        this.servicioBusqueda = new ServicioBusquedaImpl(repositorioObra,repositorioArtista);
    }

    @Test
    public void queSePuedaBuscarUnaObraPorSuTitulo() {
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Obra obra3 = new Obra();

        obra1.setTitulo("Titulo 1");
        obra2.setTitulo("Titulo 2");
        obra3.setTitulo("Titulo 3");

        when(repositorioObra.buscarPorTitulo("2")).thenReturn(List.of(obra2));

        List<Obra> obras = servicioBusqueda.buscarObraPorTitulo("2");

        assert(obras.size() == 1);
        assert(obras.get(0).getTitulo().equals("Titulo 2"));
    }

    @Test
    public void queAlBuscarUnaObraPorUnTituloInexistenteSeDevuelvaMensajeDeError() {
        when(repositorioObra.buscarPorTitulo("Titulo Inexistente")).thenReturn(List.of());

        try {
            servicioBusqueda.buscarObraPorTitulo("Titulo Inexistente");
            assert(false); // Si no lanza excepción, la prueba falla
        } catch (Exception e) {
            assert(e.getMessage().equals("No se encontraron obras con el título proporcionado."));
        }
    }

    @Test
    public void queSePuedaBuscarElPerfilDeUnArtistaPorSuNombre() {
        Artista artista = new Artista();
        artista.setNombre("Artista 1");

        when(repositorioArtista.obtenerPorNombre("Artista 1")).thenReturn(List.of(artista));

        List<Artista> artistas = servicioBusqueda.buscarArtistaPorNombre("Artista 1");

        assert(artistas.size() == 1);
        assert(artistas.get(0).getNombre().equals("Artista 1"));
    }

    @Test
    public void queAlBuscarUnNombreDeArtistaInexistenteSeMuestreMensajeDeError() {
        when(repositorioArtista.obtenerPorNombre("Nombre Inexistente")).thenReturn(List.of());

        try {
            servicioBusqueda.buscarArtistaPorNombre("Nombre Inexistente");
            assert(false); // Si no lanza excepción, la prueba falla
        } catch (Exception e) {
            assert(e.getMessage().equals("No se encontraron artistas con el nombre proporcionado."));
        }
    }

    @Test
    public void queSePuedaBuscarObrasPorSusCategorias() {
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Obra obra3 = new Obra();

        obra1.agregarCategoria(Categoria.TEST);
        obra2.agregarCategoria(Categoria.TEST);
        obra3.agregarCategoria(Categoria.ABSTRACTO);

        when(repositorioObra.obtenerPorCategoria(Categoria.TEST)).thenReturn(List.of(obra1,obra2));

        List<Obra> obras = servicioBusqueda.buscarObraPorCategoria(Categoria.TEST);

        assert(obras.size() == 2);
        assert(obras.get(0).getCategorias().contains(Categoria.TEST));
        assert(obras.get(1).getCategorias().contains(Categoria.TEST));
    }

    @Test
    public void queAlIngresarUnaCategoriaInexistenteSeMuestreMensajeDeError() {
        when(repositorioObra.obtenerPorCategoria(Categoria.TEST)).thenReturn(List.of());

        try {
            servicioBusqueda.buscarObraPorCategoria(Categoria.TEST);
            assert(false); // Si no lanza excepción, la prueba falla
        } catch (Exception e) {
            assert(e.getMessage().equals("No se encontraron obras en la categoría proporcionada."));
        }
    }

    @Test
    public void queSePuedaBuscarObrasPorRangoDePrecios() {
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Obra obra3 = new Obra();
        obra1.setPrecio(100.0);
        obra2.setPrecio(200.0);
        obra3.setPrecio(300.0);

        when(repositorioObra.obtenerPorRangoDePrecio(100.0, 250.0)).thenReturn(List.of(obra1, obra2));

        List<Obra> obras = servicioBusqueda.buscarPorRangoDePrecios(100.0, 250.0);
        assert(obras.size() == 2);
        assert(obras.get(0).getPrecio() >= 100.0 && obras.get(0).getPrecio() <= 250.0);
        assert(obras.get(1).getPrecio() >= 100.0 && obras.get(1).getPrecio() <= 250.0);
    }

    @Test
    public void queSePuedaBuscarObrasPorElNombreDeSuAutor() {
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Obra obra3 = new Obra();

        obra1.setAutor("Autor 1");
        obra2.setAutor("Autor 1");
        obra3.setAutor("Autor 2");

        when(repositorioObra.obtenerPorAutor("Autor 1")).thenReturn(List.of(obra1,obra2));
        List<Obra> obras = servicioBusqueda.buscarObraPorAutor("Autor 1");
        assert(obras.size() == 2);
        assert(obras.get(0).getAutor().equals("Autor 1"));
        assert(obras.get(1).getAutor().equals("Autor 1"));
    }

    @Test
    public void queSePuedaOrdenarLasObrasPorPrecioAscendente() {
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Obra obra3 = new Obra();

        obra1.setPrecio(100.0);
        obra2.setPrecio(400.0);
        obra3.setPrecio(300.0);

        when(repositorioObra.obtenerTodas()).thenReturn(List.of(obra1, obra2, obra3));

        List<Obra> obras = servicioBusqueda.buscarPorPrecioAscendente();

        assert(obras.size() == 3);
        assert(obras.get(0).getPrecio() <= obras.get(1).getPrecio());
        assert(obras.get(1).getPrecio() <= obras.get(2).getPrecio());
    }

    @Test
    public void queSePuedaOrdenarLasObrasPorPrecioDescendente() {
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Obra obra3 = new Obra();

        obra1.setPrecio(100.0);
        obra2.setPrecio(400.0);
        obra3.setPrecio(300.0);

        when(repositorioObra.obtenerTodas()).thenReturn(List.of(obra1, obra2, obra3));

        List<Obra> obras = servicioBusqueda.buscarPorPrecioDescendente();

        assert(obras.size() == 3);
        assert(obras.get(0).getPrecio() >= obras.get(1).getPrecio());
        assert(obras.get(1).getPrecio() >= obras.get(2).getPrecio());
    }

    @Test
    public void queSePuedaOrdenarLasObrasPorMayorCantidadDeLikes() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();
        Usuario usuario3 = new Usuario();

        usuario1.setId(1L);
        usuario2.setId(2L);
        usuario3.setId(3L);
        usuario1.setEmail("a@d.e");
        usuario2.setEmail("b@d.e");
        usuario3.setEmail("c@d.e");

        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Obra obra3 = new Obra();

        obra1.setUsuariosQueDieronLike(Set.of(usuario1, usuario2)); // 2 likes
        obra2.setUsuariosQueDieronLike(Set.of(usuario1)); // 1 like
        obra3.setUsuariosQueDieronLike(Set.of(usuario1, usuario2, usuario3)); // 3 likes

        when(repositorioObra.obtenerTodas()).thenReturn(List.of(obra1, obra2, obra3));

        List<Obra> obras = servicioBusqueda.buscarPorMayorCantidadDeLikes();

        assert(obras.size() == 3);
        assert(obras.get(0).getUsuariosQueDieronLike().size() >= obras.get(1).getUsuariosQueDieronLike().size());
        assert(obras.get(1).getUsuariosQueDieronLike().size() >= obras.get(2).getUsuariosQueDieronLike().size());
    }

    @Test
    public void queSePuedaOrdenarLasObrasPorMenorCantidadDeLikes() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();
        Usuario usuario3 = new Usuario();

        usuario1.setId(1L);
        usuario2.setId(2L);
        usuario3.setId(3L);
        usuario1.setEmail("a@d.e");
        usuario2.setEmail("b@d.e");
        usuario3.setEmail("c@d.e");

        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Obra obra3 = new Obra();

        obra1.setUsuariosQueDieronLike(Set.of(usuario1, usuario2)); // 2 likes
        obra2.setUsuariosQueDieronLike(Set.of(usuario1)); // 1 like
        obra3.setUsuariosQueDieronLike(Set.of(usuario1, usuario2, usuario3)); // 3 likes

        when(repositorioObra.obtenerTodas()).thenReturn(List.of(obra1, obra2, obra3));

        List<Obra> obras = servicioBusqueda.buscarPorMenorCantidadDeLikes();

        assert(obras.size() == 3);
        assert(obras.get(0).getUsuariosQueDieronLike().size() <= obras.get(1).getUsuariosQueDieronLike().size());
        assert(obras.get(1).getUsuariosQueDieronLike().size() <= obras.get(2).getUsuariosQueDieronLike().size());
    }

    @Test
    public void queSePuedaBuscarObrasPorPalabrasClaveEnLaDescripcion() {
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Obra obra3 = new Obra();

        obra1.setDescripcion("Esta es una hermosa obra de arte.");
        obra2.setDescripcion("Una pieza del arte plástico impresionante.");
        obra3.setDescripcion("Un paisaje natural maravilloso.");

        when(repositorioObra.buscarPorDescripcion("arte")).thenReturn(List.of(obra1, obra2));

        List<Obra> obras = servicioBusqueda.buscarObraPorDescripcion("arte");

        assert(obras.size() == 2);
        assert(obras.get(0).getDescripcion().toLowerCase().contains("arte"));
        assert(obras.get(1).getDescripcion().toLowerCase().contains("arte"));
    }
}
