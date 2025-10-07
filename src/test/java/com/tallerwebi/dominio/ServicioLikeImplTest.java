package com.tallerwebi.dominio;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class ServicioLikeImplTest {

    @Test
    public void queElUsuarioPuedaDarLikeAUnaObra() {
        ServicioLikeImpl servicio = new ServicioLikeImpl();
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        
        Boolean resultado = servicio.darLike(usuario, obra);
        
        assertThat(true, is(equalTo(resultado)));
        assertThat(obra.getUsuariosQueDieronLike().contains(usuario), is(true));
    }
    
    @Test
    public void unUsuarioNoPuedeDarLikeDosVecesALaMismaObra() {
        ServicioLikeImpl servicio = new ServicioLikeImpl();
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        
        Boolean resultado = servicio.darLike(usuario, obra);
        Boolean resultado2 = servicio.darLike(usuario, obra);
        
        assertThat(resultado, is(true));
        assertThat(resultado2, is(false));
    }

    @Test
    public void queElUsuarioPuedaQuitarElLikeDeUnaObra() {
        ServicioLikeImpl servicio = new ServicioLikeImpl();
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        
        Boolean resultado = servicio.darLike(usuario, obra);
        assertThat(resultado, is(true));
        assertThat(obra.getUsuariosQueDieronLike().contains(usuario), is(true));
        assertThat(obra.getCantidadLikes(), is(1));
        
        Boolean resultado2 = servicio.quitarLike(usuario, obra);
        assertThat(resultado2, is(true));
        assertThat(obra.getCantidadLikes(), is(0));
    }

    @Test
    public void queNoSePuedaDarLikeSiNoEstaLogueado() {
        ServicioLikeImpl servicio = new ServicioLikeImpl();
        Obra obra = new Obra();

        Boolean resultado = servicio.darLike(null, obra);

        assertThat(false, is(equalTo(resultado)));
        assertThat(obra.getUsuariosQueDieronLike().contains(null), is(false));
    }
}
