package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.tallerwebi.infraestructura.ObraDto;
import com.tallerwebi.infraestructura.ServicioLikeImpl;

public class ServicioLikeImplTest {

    @Test
    public void queElUsuarioPuedaDarLikeAUnaObra() {
        ServicioLikeImpl servicio = new ServicioLikeImpl();
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        ObraDto obraDto = new ObraDto(obra);
        
        Boolean resultado = servicio.darLike(usuario, obraDto);
        
        assertTrue(resultado);
        assertEquals(true, obraDto.getLikes().get(usuario));
        
    }
    
    @Test
    public void unUsuarioNoPuedeDarLikeDosVecesALaMismaObra() {
        ServicioLikeImpl servicio = new ServicioLikeImpl();
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        ObraDto obraDto = new ObraDto(obra);
        
        Boolean resultado = servicio.darLike(usuario, obraDto);
        Boolean resultado2 = servicio.darLike(usuario, obraDto);
        
        assertTrue(resultado);
        assertFalse(resultado2);
    }

    @Test
    public void queElUsuarioPuedaQuitarElLikeDeUnaObra() {
        ServicioLikeImpl servicio = new ServicioLikeImpl();
        Usuario usuario = new Usuario();
        Obra obra = new Obra();
        ObraDto obraDto = new ObraDto(obra);
        
        Boolean resultado = servicio.darLike(usuario, obraDto);
        assertTrue(resultado);
        assertEquals(true, obraDto.getLikes().get(usuario));
        assertEquals(1, obraDto.getLikes().size());
        
        Boolean resultado2 = servicio.quitarLike(usuario, obraDto);
        assertTrue(resultado2);
        assertEquals(0, obraDto.getLikes().size());
    }

    public void queNoSePuedaDarLikeSiNoEstaLogueado() {
    }
}
