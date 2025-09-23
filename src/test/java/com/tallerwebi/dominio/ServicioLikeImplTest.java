package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.tallerwebi.presentacion.ObraDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.empty;

public class ServicioLikeImplTest {

    @Test
    public void queElUsuarioPuedaDarLikeAUnaObra() {
        ServicioLikeImpl servicio = new ServicioLikeImpl();
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        ObraDto obraDto = new ObraDto(obra);
        
        Boolean resultado = servicio.darLike(usuario, obraDto);
        
        assertThat(true, is(equalTo(resultado)));
        assertThat(obraDto.getLikes().get(usuario), is(true));
        
    }
    
    @Test
    public void unUsuarioNoPuedeDarLikeDosVecesALaMismaObra() {
        ServicioLikeImpl servicio = new ServicioLikeImpl();
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        ObraDto obraDto = new ObraDto(obra);
        
        Boolean resultado = servicio.darLike(usuario, obraDto);
        Boolean resultado2 = servicio.darLike(usuario, obraDto);
        
        assertThat(resultado, is(true));
        assertThat(resultado2, is(false));
    }

    @Test
    public void queElUsuarioPuedaQuitarElLikeDeUnaObra() {
        ServicioLikeImpl servicio = new ServicioLikeImpl();
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        ObraDto obraDto = new ObraDto(obra);
        
        Boolean resultado = servicio.darLike(usuario, obraDto);
        assertThat(resultado, is(true));
        assertThat(obraDto.getLikes().get(usuario), is(true));
        assertThat(obraDto.getLikes().size(), is(1));
        
        Boolean resultado2 = servicio.quitarLike(usuario, obraDto);
        assertThat(resultado2, is(true));
        assertThat(obraDto.getLikes().entrySet(), is(empty()));
    }

    public void queNoSePuedaDarLikeSiNoEstaLogueado() {
    }
}
