package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoExisteFormatoObra;
import com.tallerwebi.dominio.repositorios.RepositorioFormatoObra;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.servicioImpl.ServicioFormatoObraImpl;
import com.tallerwebi.dominio.servicioImpl.ServicioGaleriaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioFormatoObraImplTest {

    @Mock
    private RepositorioFormatoObra repositorioFormatoObra;

    @Mock
    private RepositorioObra repositorioObra;

    @InjectMocks
    private ServicioFormatoObraImpl servicio;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void crearFormato_creaCuandoObraExiste() {
        Long obraId = 1L;
        Obra obra = new Obra();
        obra.setId(obraId);

        when(repositorioObra.obtenerPorId(obraId)).thenReturn(obra);

        FormatoObra resultado = servicio.crearFormato(obraId, Formato.DIGITAL, 1200.0, 5);

        assertThat(resultado, is(notNullValue()));
        assertThat(resultado.getObra(), is(equalTo(obra)));
        assertThat(obra.getFormatos(), hasSize(1));
        assertThat(obra.getFormatos().iterator().next(), is(resultado));
        assertThat(resultado.getFormato(), is(equalTo(Formato.DIGITAL)));
        assertThat(resultado.getPrecio(), is(equalTo(1200.0)));
        assertThat(resultado.getStock(), is(equalTo(5)));
    }

    @Test
    public void crearFormato_lanzaSiObraNoExiste() {
        Long obraId = 99L;
        when(repositorioObra.obtenerPorId(obraId)).thenReturn(null);

        assertThrows(NoExisteLaObra.class, () ->
                servicio.crearFormato(obraId, Formato.ORIGINAL, 2000.0, 2)
        );

        verify(repositorioFormatoObra, never()).guardar(any());
    }

    @Test
    public void eliminarFormato_eliminaCuandoExiste() {
        Obra obra = new Obra();
        Long formatoId = 10L;
        FormatoObra formato = new FormatoObra();
        formato.setId(formatoId);
        obra.setId(1L);
        formato.setObra(obra);
        obra.setFormatos(new HashSet<>(List.of(formato)));

        when(repositorioFormatoObra.obtenerPorId(formatoId)).thenReturn(formato);

        servicio.eliminarFormato(formatoId);

        verify(repositorioFormatoObra, times(1)).eliminar(formato);

        assertThat(obra.getFormatos(), hasSize(0));
    }

    @Test
    public void eliminarFormato_lanzaSiNoExiste() {
        Long formatoId = 999L;
        when(repositorioFormatoObra.obtenerPorId(formatoId)).thenReturn(null);

        assertThrows(NoExisteFormatoObra.class, () ->
                servicio.eliminarFormato(formatoId)
        );

        verify(repositorioFormatoObra, never()).eliminar(any());
    }

    @Test
    public void actualizarPrecio_actualizaCuandoExiste() {
        Obra obra = new Obra();
        obra.setId(1L);
        obra.setTitulo("titulo");

        Long formatoId = 5L;
        FormatoObra formato = new FormatoObra();
        formato.setId(formatoId);
        formato.setPrecio(1000.0);
        formato.setObra(obra);
        obra.setFormatos(new HashSet<>(List.of(formato)));

        when(repositorioFormatoObra.obtenerPorId(formatoId)).thenReturn(formato);

        servicio.actualizarPrecio(formatoId, 1500.0);

        assertThat(formato.getPrecio(), is(equalTo(1500.0)));

        verify(repositorioFormatoObra, times(1)).guardar(formato);

        assertThat(obra.getFormatos().iterator().next().getPrecio(), is(equalTo(formato.getPrecio())));
    }

    @Test
    public void actualizarPrecio_lanzaSiNoExisteFormato() {
        Long formatoId = 1234L;
        when(repositorioFormatoObra.obtenerPorId(formatoId)).thenReturn(null);

        assertThrows(NoExisteFormatoObra.class, () ->
                servicio.actualizarPrecio(formatoId, 2000.0)
        );

        verify(repositorioFormatoObra, never()).guardar(any());
    }

    @Test
    public void actualizarStock_actualizaCuandoExiste() {
        Obra obra = new Obra();
        obra.setId(1L);
        obra.setTitulo("titulo");

        Long formatoId = 7L;
        FormatoObra formato = new FormatoObra();
        formato.setId(formatoId);
        formato.setStock(3);
        formato.setObra(obra);
        obra.setFormatos(new HashSet<>(List.of(formato)));

        when(repositorioFormatoObra.obtenerPorId(formatoId)).thenReturn(formato);

        servicio.actualizarStock(formatoId, 25);

        assertThat(formato.getStock(), is(equalTo(25)));
        verify(repositorioFormatoObra, times(1)).guardar(formato);
        assertThat(obra.getFormatos().iterator().next().getStock(), is(equalTo(formato.getStock())));
    }

    @Test
    public void actualizarStock_lanzaSiNoExisteFormato() {
        Long formatoId = 777L;
        when(repositorioFormatoObra.obtenerPorId(formatoId)).thenReturn(null);

        assertThrows(NoExisteFormatoObra.class, () ->
                servicio.actualizarStock(formatoId, 10)
        );

        verify(repositorioFormatoObra, never()).guardar(any());
    }

    @Test
    public void obtenerPorId_retornaCuandoExiste() {
        Long formatoId = 2L;
        FormatoObra formato = new FormatoObra();
        formato.setId(formatoId);

        when(repositorioFormatoObra.obtenerPorId(formatoId)).thenReturn(formato);

        FormatoObra encontrado = servicio.obtenerPorId(formatoId);

        assertThat(encontrado, is(notNullValue()));
        assertThat(encontrado.getId(), is(equalTo(formatoId)));
    }

    @Test
    public void obtenerPorId_lanzaSiNoExiste() {
        Long formatoId = 222L;
        when(repositorioFormatoObra.obtenerPorId(formatoId)).thenReturn(null);

        assertThrows(NoExisteFormatoObra.class, () ->
                servicio.obtenerPorId(formatoId)
        );
    }

    @Test
    public void obtenerFormatosPorObra_retornaListaInclusoVacia() {
        Long obraId = 11L;

        when(repositorioFormatoObra.obtenerFormatosPorObra(obraId)).thenReturn(Collections.emptyList());

        List<FormatoObra> lista = servicio.obtenerFormatosPorObra(obraId);

        assertThat(lista, is(notNullValue()));
        assertThat(lista, is(empty()));

        FormatoObra f1 = new FormatoObra();
        f1.setId(1L);
        FormatoObra f2 = new FormatoObra();
        f2.setId(2L);

        when(repositorioFormatoObra.obtenerFormatosPorObra(obraId)).thenReturn(Arrays.asList(f1, f2));

        lista = servicio.obtenerFormatosPorObra(obraId);

        assertThat(lista, hasSize(2));
        assertThat(lista, containsInAnyOrder(f1, f2));
    }

    @Test
    public void queSePuedanObtenerLosFormatosFaltantesDeUnaObra() {
        Long obraId = 20L;

        FormatoObra formato1 = new FormatoObra();
        formato1.setFormato(Formato.DIGITAL);

        FormatoObra formato2 = new FormatoObra();
        formato2.setFormato(Formato.ORIGINAL);

        when(repositorioFormatoObra.obtenerFormatosPorObra(obraId))
                .thenReturn(Arrays.asList(formato1, formato2));

        List<Formato> formatosFaltantes = servicio.obtenerFormatosFaltantesPorObra(obraId);

        List<Formato> todosLosFormatos = Arrays.asList(Formato.values());
        List<Formato> formatosExistentes = Arrays.asList(Formato.DIGITAL, Formato.ORIGINAL);

        assertThat(formatosFaltantes, hasSize(todosLosFormatos.size() - formatosExistentes.size()));
        assertThat(formatosFaltantes, not(containsInAnyOrder(formatosExistentes.toArray(new Formato[0]))));

        verify(repositorioFormatoObra).obtenerFormatosPorObra(obraId);
    }

    @Test
    public void queSePuedaActualizarUnFormatoObraExistente() throws NoExisteFormatoObra {
        RepositorioFormatoObra repositorioFormatoObra = mock(RepositorioFormatoObra.class);
        RepositorioObra repositorioObra = mock(RepositorioObra.class);

        ServicioFormatoObraImpl servicio = new ServicioFormatoObraImpl(repositorioFormatoObra, repositorioObra);

        Long formatoId = 1L;
        Double nuevoPrecio = 2500.0;
        Integer nuevoStock = 15;

        Obra obra = new Obra();
        FormatoObra formatoExistente = new FormatoObra(obra, Formato.DIGITAL, 1000.0, 5);
        formatoExistente.setId(formatoId);

        when(repositorioFormatoObra.obtenerPorId(formatoId)).thenReturn(formatoExistente);

        servicio.actualizarFormatoObra(formatoId, nuevoPrecio, nuevoStock);

        verify(repositorioFormatoObra, times(1)).obtenerPorId(formatoId);
        verify(repositorioFormatoObra, times(1)).guardar(formatoExistente);

        assertThat(formatoExistente.getPrecio(), is(nuevoPrecio));
        assertThat(formatoExistente.getStock(), is(nuevoStock));
    }

    @Test
    public void queAlIntentarActualizarUnFormatoInexistenteSeLanceExcepcion() {
        RepositorioFormatoObra repositorioFormatoObra = mock(RepositorioFormatoObra.class);
        RepositorioObra repositorioObra = mock(RepositorioObra.class);

        ServicioFormatoObraImpl servicio = new ServicioFormatoObraImpl(repositorioFormatoObra, repositorioObra);

        Long formatoId = 99L;
        Double nuevoPrecio = 3000.0;
        Integer nuevoStock = 20;

        when(repositorioFormatoObra.obtenerPorId(formatoId)).thenReturn(null);

        assertThrows(NoExisteFormatoObra.class, () ->
                servicio.actualizarFormatoObra(formatoId, nuevoPrecio, nuevoStock)
        );

        verify(repositorioFormatoObra, never()).guardar(any());
    }
}
