package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.servicioImpl.ServicioEstadisticaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

public class ServicioEstadisticaTest {

    RepositorioObra repositorioObra;
    ServicioEstadistica servicioEstadistica;
    Usuario usuario;
    Artista artista;
    List<Usuario> mockUsuarios;

    @BeforeEach
    public void setUp() {
        this.usuario = mock(Usuario.class);
        this.repositorioObra = mock(RepositorioObra.class);
        this.servicioEstadistica = new ServicioEstadisticaImpl(repositorioObra);

        this.artista = new Artista();
        this.artista.setId(1L);
        this.artista.setNombre("Artista");
        this.artista.setUsuario(usuario);

        when(usuario.getId()).thenReturn(1L);

        this.mockUsuarios = List.of(mock(Usuario.class),
                                    mock(Usuario.class),
                                    mock(Usuario.class),
                                    mock(Usuario.class),
                                    mock(Usuario.class),
                                    mock(Usuario.class),
                                    mock(Usuario.class),
                                    mock(Usuario.class),
                                    mock(Usuario.class),
                                    mock(Usuario.class));
    }

    @Test
    public void queSePuedanObtenerLasObrasDelArtistaPorCantidadDeVentas() {
        List<Obra> obrasDePrueba = cargarObrasDePrueba();

        Map<Obra, Long> ventasMock = new LinkedHashMap<>();
        ventasMock.put(obrasDePrueba.get(0), 10L);
        ventasMock.put(obrasDePrueba.get(1), 7L);
        ventasMock.put(obrasDePrueba.get(2), 4L);

        when(repositorioObra.obtenerMasVendidasPorArtista(artista))
                .thenReturn(ventasMock);

        Map<Obra, Long> resultado = servicioEstadistica.obtenerMasVendidasArtista(artista);

        assertThat(resultado.size(), is(3));
        assertThat(resultado.get(obrasDePrueba.get(0)), is(10L));
        assertThat(resultado.get(obrasDePrueba.get(1)), is(7L));
        assertThat(resultado.get(obrasDePrueba.get(2)), is(4L));

        verify(repositorioObra, times(1)).obtenerMasVendidasPorArtista(artista);
    }

    @Test
    public void queSePuedanObtenerLasObrasDelArtistaPorCantidadDeLikes() {
        List<Obra> obrasDePrueba = cargarObrasDePrueba();

        // mock: cantidad de likes por obra
        List<Obra> likesMock = new ArrayList<>();
        likesMock.add(obrasDePrueba.get(3));  // obra4 - 9 likes
        likesMock.add(obrasDePrueba.get(1));  // obra2 - 7 likes
        likesMock.add(obrasDePrueba.get(4));  // obra5 - 6 likes

        when(repositorioObra.obtenerMasLikeadasPorArtista(artista))
                .thenReturn(likesMock);

        Map<Obra, Long> resultado = servicioEstadistica.obtenerMasLikeadasArtista(artista);

        assertThat(resultado.size(), is(3));
        assertThat(resultado.get(obrasDePrueba.get(3)), is(9L));
        assertThat(resultado.get(obrasDePrueba.get(1)), is(7L));
        assertThat(resultado.get(obrasDePrueba.get(4)), is(6L));

        verify(repositorioObra, times(1)).obtenerMasLikeadasPorArtista(artista);
    }

    @Test
    public void queSePuedaObtenerElTop3DeCategoriasMasVendidasParaUnArtista() {
        Map<Categoria, Long> mockCategorias = new LinkedHashMap<>();
        mockCategorias.put(Categoria.PINTURA, 20L);
        mockCategorias.put(Categoria.MODERNO, 12L);
        mockCategorias.put(Categoria.RETRATO, 8L);

        when(repositorioObra.obtenerTresCategoriasMasVendidasArtista(artista))
                .thenReturn(mockCategorias);

        Map<Categoria, Long> resultado =
                servicioEstadistica.obtenerTresCategoriasMasVendidasArtista(artista);

        assertThat(resultado.size(), is(3));
        assertThat(resultado.get(Categoria.PINTURA), is(20L));
        assertThat(resultado.get(Categoria.MODERNO), is(12L));
        assertThat(resultado.get(Categoria.RETRATO), is(8L));

        verify(repositorioObra, times(1)).obtenerTresCategoriasMasVendidasArtista(artista);
    }

    @Test
    public void queSePuedaObtenerElTop3DeCategoriasMasLikeadasParaUnArtista() {
        Map<Categoria, Long> mockCategorias = new LinkedHashMap<>();
        mockCategorias.put(Categoria.MODERNO, 14L);
        mockCategorias.put(Categoria.PINTURA, 12L);
        mockCategorias.put(Categoria.FOTOGRAFIA, 9L);

        when(repositorioObra.obtenerTresCategoriasMasLikeadasArtista(artista))
                .thenReturn(mockCategorias);

        Map<Categoria, Long> resultado =
                servicioEstadistica.obtenerTresCategoriasMasLikeadasArtista(artista);

        assertThat(resultado.size(), is(3));
        assertThat(resultado.get(Categoria.MODERNO), is(14L));
        assertThat(resultado.get(Categoria.PINTURA), is(12L));
        assertThat(resultado.get(Categoria.FOTOGRAFIA), is(9L));

        verify(repositorioObra, times(1)).obtenerTresCategoriasMasLikeadasArtista(artista);
    }

    @Test
    public void queSePuedanObtenerLasObrasEnTrendingDeUnArtistaTeniendoEnCuentaLasVentas() {
        List<Obra> obras = cargarObrasDePrueba();

        List<Obra> trendingMock = List.of(
                obras.get(0),  // obra1
                obras.get(1),  // obra2
                obras.get(2)   // obra3
        );

        when(repositorioObra.obtenerTrendingVentasArtista(artista))
                .thenReturn(trendingMock);

        List<Obra> resultado = servicioEstadistica.obtenerTrendingVentasArtista(artista);

        assertThat(resultado.size(), is(3));
        assertThat(resultado.get(0), is(obras.get(0)));
        assertThat(resultado.get(1), is(obras.get(1)));
        assertThat(resultado.get(2), is(obras.get(2)));

        verify(repositorioObra, times(1)).obtenerTrendingVentasArtista(artista);
    }

    @Test
    public void queSePuedanObtenerLasObrasEnTrendingDeUnArtistaTeniendoEnCuentaLosLikes() {
        List<Obra> obras = cargarObrasDePrueba();

        List<Obra> trendingMock = List.of(
                obras.get(3),  // obra4 (9 likes)
                obras.get(1),  // obra2 (7 likes)
                obras.get(4)   // obra5 (6 likes)
        );

        when(repositorioObra.obtenerTrendingLikesArtista(artista))
                .thenReturn(trendingMock);

        List<Obra> resultado = servicioEstadistica.obtenerTrendingLikesArtista(artista);

        assertThat(resultado.size(), is(3));
        assertThat(resultado.get(0), is(obras.get(3)));
        assertThat(resultado.get(1), is(obras.get(1)));
        assertThat(resultado.get(2), is(obras.get(4)));

        verify(repositorioObra, times(1)).obtenerTrendingLikesArtista(artista);
    }

    @Test
    public void queAlNoTenerObrasElArtistaNoSeObtenganEstadisticas() {
        when(repositorioObra.obtenerMasVendidasPorArtista(artista))
                .thenReturn(Collections.emptyMap());

        Map<Obra, Long> resultado = servicioEstadistica.obtenerMasVendidasArtista(artista);

        assertThat(resultado.isEmpty(), is(true));

        verify(repositorioObra, times(1)).obtenerMasVendidasPorArtista(artista);
    }

    private List<Obra> cargarObrasDePrueba() {
        Obra obra1 = new Obra();
        Obra obra2 = new Obra();
        Obra obra3 = new Obra();
        Obra obra4 = new Obra();
        Obra obra5 = new Obra();

        obra1.setId(1L);
        obra2.setId(2L);
        obra3.setId(3L);
        obra4.setId(4L);
        obra5.setId(5L);

        obra1.setTitulo("titulo1");
        obra2.setTitulo("titulo2");
        obra3.setTitulo("titulo3");
        obra4.setTitulo("titulo4");
        obra5.setTitulo("titulo5");

        obra1.setCategorias(new HashSet<>(List.of(Categoria.PINTURA, Categoria.MODERNO)));
        obra2.setCategorias(new HashSet<>(List.of(Categoria.RETRATO, Categoria.PINTURA)));
        obra3.setCategorias(new HashSet<>(List.of(Categoria.ESCULTURA)));
        obra4.setCategorias(new HashSet<>(List.of(Categoria.FOTOGRAFIA, Categoria.MODERNO)));
        obra5.setCategorias(new HashSet<>(List.of(Categoria.PINTURA)));

        obra1.setUsuariosQueDieronLike(new HashSet<>(mockUsuarios.subList(0, 5)));
        obra2.setUsuariosQueDieronLike(new HashSet<>(mockUsuarios.subList(0, 7)));
        obra3.setUsuariosQueDieronLike(new HashSet<>(mockUsuarios.subList(0, 3)));
        obra4.setUsuariosQueDieronLike(new HashSet<>(mockUsuarios.subList(0, 9)));
        obra5.setUsuariosQueDieronLike(new HashSet<>(mockUsuarios.subList(0, 6)));

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);
        obra5.setArtista(artista);

        return List.of(obra1, obra2, obra3, obra4, obra5);
    }
}
