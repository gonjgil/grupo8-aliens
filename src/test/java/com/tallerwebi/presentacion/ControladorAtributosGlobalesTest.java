package com.tallerwebi.presentacion;

import org.mockito.Mock;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.Usuario;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

public class ControladorAtributosGlobalesTest {
    
    @Test
    void getContadorCarrito_devuelveLaCantidadCorrecta() {
        ServicioCarrito servicioCarrito = mock(ServicioCarrito.class);
        ControladorAtributosGlobales controlador = new ControladorAtributosGlobales(servicioCarrito);

        Usuario usuario = new Usuario();
        when(servicioCarrito.contarItemsEnCarrito(any())).thenReturn(5);

        int resultado = controlador.getContadorCarrito(usuario);

        assertThat(resultado, is(5));
    }
}
