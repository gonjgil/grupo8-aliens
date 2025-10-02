package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ObraDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ServicioCarritoImplTest {

    private RepositorioObra repositorioObra;

    @BeforeEach
    public void init(){
        this.repositorioObra = mock(RepositorioObra.class);
    }

    @Test
    public void obtenerObra_DebeDevolver0SiNoAgregoNingunaObraAlCarrito() {
        //preparacion

   //     RepositorioObra repositorioObra = mock(RepositorioObra.class);
        when(this.repositorioObra.obtenerTodas()).thenReturn(Collections.emptyList());

        ServicioCarritoImpl servicio = new ServicioCarritoImpl(this.repositorioObra);
        //ejecucion
        List<ObraDto> resultado = servicio.obtenerObras();

        assertThat(resultado.size(), is(0));

    }

    @Test
    public void dadoQueSeAgregaUnaObraAlCarritoVerificarQueSeHayaAgregadoCorrectamente() {
    //    RepositorioObra repositorioObra = mock(RepositorioObra.class);
        Obra obra1 = new Obra();
        obra1.setId(1L);
        //obra1.setStock(2);

        when(this.repositorioObra.hayStock(eq(1L))).thenReturn(true);

        when(this.repositorioObra.obtenerPorId(1L)).thenReturn(obra1);

        ServicioCarritoImpl servicio = new ServicioCarritoImpl(this.repositorioObra);

        servicio.agregarObraAlCarrito(obra1);

        assertThat(servicio.obtenerObras().size(), is(1));
    }

    @Test
    public void obtenerObra_DebeDevolver3ObrasSiSeAgregan3ObrasAlCarrito() {
        //preparacion
        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setStock(2);

        Obra obra2 = new Obra();
        obra2.setId(2L);
        obra2.setStock(2);

        Obra obra3 = new Obra();
        obra3.setId(3L);
        obra3.setStock(2);

        // Simula que las obras existen en el repositorio
        when(this.repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(this.repositorioObra.obtenerPorId(2L)).thenReturn(obra2);
        when(this.repositorioObra.obtenerPorId(3L)).thenReturn(obra3);
       // when(repositorioObra.obtenerTodas()).thenReturn(Arrays.asList(obra1, obra2, obra3));

        // Simula que hay stock suficiente para cada obra
        when(this.repositorioObra.hayStock(eq(1L))).thenReturn(true);
        when(this.repositorioObra.hayStock(eq(2L))).thenReturn(true);
        when(this.repositorioObra.hayStock(eq(3L))).thenReturn(true);

        ServicioCarritoImpl servicio = new ServicioCarritoImpl(repositorioObra);

        //ejecucion
        servicio.agregarObraAlCarrito(obra1);
        servicio.agregarObraAlCarrito(obra2);
        servicio.agregarObraAlCarrito(obra3);

        List<ObraDto> resultado = servicio.obtenerObras();

        assertThat(resultado.size(), is(3));

    }

    @Test
    public void debeVerificarQueNoSeAgregueObraACarritoSiNoHayStock() {
        Obra obra1 = new Obra();
        obra1.setId(1L);

        Obra obra2 = new Obra();
        obra2.setId(2L);

        when(this.repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(this.repositorioObra.obtenerPorId(2L)).thenReturn(obra2);

        when(this.repositorioObra.hayStock(eq(1L))).thenReturn(true);
        when(this.repositorioObra.hayStock(eq(2L))).thenReturn(false);

        ServicioCarritoImpl servicio = new ServicioCarritoImpl(repositorioObra);
        servicio.agregarObraAlCarrito(obra1);
        servicio.agregarObraAlCarrito(obra2);
        List<ObraDto> resultado = servicio.obtenerObras();

        assertThat(resultado.size(), is(1));

    }

//    @Test
//    public void debeVerificarQueNoSePuedaAgregarUnaMismaObraACarritoSiNoHayStock() {
//        Obra obra1 = new Obra();
//        obra1.setId(1L);
//        obra1.setStock(4);
//
//        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
//        when(repositorioObra.hayStock(1L)).thenAnswer(invocation -> obra1.getStock() > 0);
//        doAnswer(invocation -> {
//            obra1.setStock(obra1.getStock() - 1);
//            return null;
//        }).when(repositorioObra).descontarStock(obra1);
//
//
//        ServicioCarritoImpl servicio = new ServicioCarritoImpl(this.repositorioObra);
//        servicio.agregarObraAlCarrito(obra1);
//        servicio.agregarObraAlCarrito(obra1);
//        servicio.agregarObraAlCarrito(obra1);
//        servicio.agregarObraAlCarrito(obra1);
//        servicio.agregarObraAlCarrito(obra1);
//
//
//        List<ObraDto> resultado = servicio.obtenerObras();
//
//        assertThat(resultado.size(), is(4));
//    }


    @Test
    public void debeVerificarQueNoSePuedaAgregarUnaMismaObraACarritoSiNoHayStock() {
        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setStock(4);

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(repositorioObra.hayStock(1L)).thenReturn(true);

        ServicioCarritoImpl servicio = new ServicioCarritoImpl(repositorioObra);

        servicio.agregarObraAlCarrito(obra1);
        servicio.agregarObraAlCarrito(obra1);
        servicio.agregarObraAlCarrito(obra1);
        servicio.agregarObraAlCarrito(obra1);

        // Quinta llamada: stock ya es 0, no debería agregarse
        when(repositorioObra.hayStock(1L)).thenReturn(false);
        servicio.agregarObraAlCarrito(obra1);

        List<ObraDto> resultado = servicio.obtenerObras();
        assertThat(resultado.size(), is(4));

    }

    @Test
    public void seDebeVerificarQueSePuedaEliminarSoloUnaObraSiHayDosIgualesEnElCarrito() {
        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setStock(4);

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(repositorioObra.hayStock(1L)).thenReturn(true);

        ServicioCarritoImpl servicio = new ServicioCarritoImpl(repositorioObra);

        servicio.agregarObraAlCarrito(obra1);
        servicio.agregarObraAlCarrito(obra1);

        List<ObraDto> resultado = servicio.obtenerObras();
        assertThat(resultado.size(), is(2));

        servicio.eliminarObraDelCarrito(obra1);
        resultado = servicio.obtenerObras();
        assertThat(resultado.size(), is(1));

    }

    @Test
    public void vaciarCarrito_seDebeVerificarQueElCarritoSeVacieCorrectamente() { //?
        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setStock(4);

        Obra obra2 = new Obra();
        obra1.setId(2L);
        obra1.setStock(4);

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(repositorioObra.obtenerPorId(2L)).thenReturn(obra2);

        when(repositorioObra.hayStock(1L)).thenReturn(true);
        when(repositorioObra.hayStock(2L)).thenReturn(true);

        ServicioCarritoImpl servicio = new ServicioCarritoImpl(repositorioObra);

        servicio.agregarObraAlCarrito(obra1);
        servicio.agregarObraAlCarrito(obra2);
        servicio.vaciarCarrito();

        List<ObraDto> resultado = servicio.obtenerObras();

        assertThat(resultado.size(), is(0));

    }

    @Test
    public void getCantidadTotal_devuelvaLaCantidadCorrectaDeObrasAgregadasAlCarrito() {
        Obra obra1 = new Obra();
        obra1.setId(1L);

        Obra obra2 = new Obra();
        obra2.setId(2L);

        Obra obra3 = new Obra();
        obra3.setId(3L);

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(repositorioObra.obtenerPorId(2L)).thenReturn(obra2);
        when(repositorioObra.obtenerPorId(3L)).thenReturn(obra3);

        when(repositorioObra.hayStock(1L)).thenReturn(true);
        when(repositorioObra.hayStock(2L)).thenReturn(true);
        when(repositorioObra.hayStock(3L)).thenReturn(true);

        ServicioCarritoImpl servicio = new ServicioCarritoImpl(repositorioObra);

        servicio.agregarObraAlCarrito(obra1);
        servicio.agregarObraAlCarrito(obra2);

        List<ObraDto> resultado = servicio.obtenerObras();

        assertThat(servicio.getCantidadTotal(), is(2));
        assertThat(resultado.get(0).getId(), is(equalTo(1L)));
        assertThat(resultado.get(1).getId(), is(equalTo(2L)));

    }
}
