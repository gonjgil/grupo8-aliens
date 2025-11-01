package com.tallerwebi.dominio;

import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.servicioImpl.ServicioLikeImpl;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ServicioLikeImplTest {

    @Test
    public void queElUsuarioPuedaDarLikeAUnaObra() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioLikeImpl servicio = new ServicioLikeImpl(repositorioObra);
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        Long obraId = 1L;
        obra.setId(obraId);

        when(repositorioObra.obtenerPorId(obraId)).thenReturn(obra);
        
        servicio.toggleLike(usuario, obraId);

        assertThat(obra.getUsuariosQueDieronLike().contains(usuario), is(true));
        assertThat(obra.getCantidadLikes(), is(1));
    }

    @Test
    public void siUnDaLikeDosVecesALaMismaObraLoRemueve() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioLikeImpl servicio = new ServicioLikeImpl(repositorioObra);
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        Long obraId = 1L;
        obra.setId(obraId);

        when(repositorioObra.obtenerPorId(obraId)).thenReturn(obra);

        servicio.toggleLike(usuario, obraId);
        servicio.toggleLike(usuario, obraId);

        assertThat(obra.getCantidadLikes(), is(0));
    }

    @Test
    public void queNoSePuedaDarLikeSiNoEstaLogueado() {
        RepositorioObra repositorioObra = mock(RepositorioObra.class);
        ServicioLikeImpl servicio = new ServicioLikeImpl(repositorioObra);
        Usuario usuario = null; // usuario no logueado
        Obra obra = new Obra();
        Long obraId = 1L;
        obra.setId(obraId);
        
        servicio.toggleLike(usuario, obraId);

        assertThat(obra.getUsuariosQueDieronLike().contains(usuario), is(false));
        assertThat(obra.getCantidadLikes(), is(0));
    }
}
