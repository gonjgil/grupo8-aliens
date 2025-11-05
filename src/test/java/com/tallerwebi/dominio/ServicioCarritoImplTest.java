package com.tallerwebi.dominio;

import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioFormatoObra;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.servicioImpl.ServicioCarritoImpl;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class ServicioCarritoImplTest {

    @Mock
    private RepositorioObra repositorioObra;

    @Mock
    private RepositorioCarrito repositorioCarrito;
    
    @Mock
    private RepositorioFormatoObra repositorioFormatoObra;

    private ServicioCarritoImpl servicioCarritoImpl;

    @BeforeEach
    public void init(){
        this.repositorioObra = mock(RepositorioObra.class);
        this.repositorioCarrito = mock(RepositorioCarrito.class);
        this.repositorioFormatoObra = mock(RepositorioFormatoObra.class);
        this.servicioCarritoImpl = new ServicioCarritoImpl(repositorioCarrito,repositorioObra,repositorioFormatoObra);
    }

    @Test
    public void dadoQueExisteUnCarritoActivoDeUsuarioObtengoElCarritoDeUsuarioEspecifico(){
        Usuario usuario = new Usuario();
        Carrito carritoExistente = new Carrito(usuario);

        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carritoExistente);

        Carrito resultado = servicioCarritoImpl.obtenerOCrearCarritoParaUsuario(usuario);

        assertThat(resultado, is(equalTo(carritoExistente)));
    }

    @Test
    public void dadoQueCarritoNoExisteParaUsuarioSeDebeCrearUno(){
        Usuario usuario = new Usuario();
        Carrito carritoNuevo = new Carrito(usuario);

        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(null);
        when(repositorioCarrito.crearCarritoParaUsuario(usuario)).thenReturn(carritoNuevo);

        Carrito resultado = servicioCarritoImpl.obtenerOCrearCarritoParaUsuario(usuario);

        assertThat(resultado, is(equalTo(carritoNuevo)));
    }

    @Test
    public void dadoQueExisteUnCarritoDeberiaAgregarObraCorrectamente() throws NoExisteLaObra, NoHayStockSuficiente {
        Usuario usuario = new Usuario();
        Obra obra1 = new Obra();
        Long obraId = 1L;
        Formato formato = Formato.ORIGINAL;
        obra1.setId(obraId);
        obra1.setStock(2);

        Carrito carrito = new Carrito(usuario);
        
        FormatoObra formatoObra = new FormatoObra();
        formatoObra.setStock(10);
        formatoObra.setFormato(formato);

        when(repositorioObra.obtenerPorId(obra1.getId())).thenReturn(obra1);
        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obraId, formato)).thenReturn(formatoObra);

        servicioCarritoImpl.agregarObraAlCarrito(usuario, obraId, formato);

        Carrito carritoResultado = servicioCarritoImpl.obtenerCarritoConItems(usuario);

        assertThat(carritoResultado.getItems().size(), is(equalTo(1)));
        assertThat(carritoResultado.getItems().get(0).getObra().getId(), is(equalTo(obraId)));
    }

    @Test
    public void debeVerificarQueNoSeAgregueObraACarritoSiNoHayStock() throws NoHayStockSuficiente {
        Obra obra1 = new Obra();
        Formato formato = Formato.ORIGINAL;
        obra1.setId(1L);
        obra1.setStock(2);

        Obra obra2 = new Obra();
        Formato formato2 = Formato.ORIGINAL;
        obra2.setId(2L);
        obra2.setStock(0);

        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        
        FormatoObra formatoObra1 = new FormatoObra();
        formatoObra1.setStock(10);
        formatoObra1.setFormato(formato);
        
        FormatoObra formatoObra2 = new FormatoObra();
        formatoObra2.setStock(0); // Sin stock para obra2
        formatoObra2.setFormato(formato);
        
        when(this.repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(this.repositorioObra.obtenerPorId(2L)).thenReturn(obra2);
        
        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra1.getId(), formato)).thenReturn(formatoObra1);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra2.getId(), formato)).thenReturn(formatoObra2);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra2.getId(), formato2)).thenReturn(formatoObra2);
        
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato);

        try {
            servicioCarritoImpl.agregarObraAlCarrito(usuario, obra2.getId(), formato2);
        } catch (NoHayStockSuficiente e) {
        }

        List<Obra> resultado = servicioCarritoImpl.obtenerObras(usuario);

        assertThat(resultado.size(), is(1));
    }

    @Test
    public void debeEliminarObraDelCarritoCorrectamente() throws NoHayStockSuficiente {
        Usuario usuario = new Usuario();
        Long obra1Id = 1L;
        Obra obra1 = new Obra();
        obra1.setId(obra1Id);
        obra1.setStock(2);

        Long obra2Id = 2L;
        Obra obra2 = new Obra();
        obra2.setId(obra2Id);
        obra2.setStock(2);

        Carrito carrito = new Carrito(usuario);
        Formato formato = Formato.ORIGINAL;
        FormatoObra formatoObra = new FormatoObra();
        formatoObra.setStock(10);
        formatoObra.setFormato(formato);
        
        when(repositorioObra.obtenerPorId(obra1Id)).thenReturn(obra1);
        when(repositorioObra.obtenerPorId(obra2Id)).thenReturn(obra2);
        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra1.getId(), formato)).thenReturn(formatoObra);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra2.getId(), formato)).thenReturn(formatoObra);

        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1Id, formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra2Id,formato);

        servicioCarritoImpl.eliminarObraDelCarrito(usuario, obra1Id, formato);

        List<Obra> obrasEnCarrito = servicioCarritoImpl.obtenerObras(usuario);
        assertThat(obrasEnCarrito.size(), is(1));
        assertThat(servicioCarritoImpl.contarItemsEnCarrito(usuario), is(1));
    }

    @Test
    public void alEliminarUnaObraDelCarritoNoDebeModificarCarritoSiLaObraNoEstaPresenteEnCarrito() {
        Usuario usuario = new Usuario();
        Long obraId = 1L;
        Formato formato = Formato.ORIGINAL;
        Obra obra = new Obra();
        obra.setId(obraId);
        Carrito carrito = new Carrito(usuario);

        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);
        when(repositorioObra.obtenerPorId(obraId)).thenReturn(obra);

        servicioCarritoImpl.eliminarObraDelCarrito(usuario, obraId, formato);

        List<Obra> obrasEnCarrito = servicioCarritoImpl.obtenerObras(usuario);
        assertThat(obrasEnCarrito.size(), is(0));
        assertThat(servicioCarritoImpl.contarItemsEnCarrito(usuario), is(0));
    }


    @Test
    public void obtenerObra_DebeDevolverTresObrasSiSeAgreganTresObrasAlCarrito() throws NoHayStockSuficiente {
        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setStock(1);

        Obra obra2 = new Obra();
        obra2.setId(2L);
        obra2.setStock(2);

        Obra obra3 = new Obra();
        obra3.setId(3L);
        obra3.setStock(3);

        Formato formato = Formato.ORIGINAL;
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);

        FormatoObra formatoObra1 = new FormatoObra();
        formatoObra1.setStock(10);
        formatoObra1.setFormato(formato);
        
        FormatoObra formatoObra2 = new FormatoObra();
        formatoObra2.setStock(10);
        formatoObra2.setFormato(formato);
        
        FormatoObra formatoObra3 = new FormatoObra();
        formatoObra3.setStock(10);
        formatoObra3.setFormato(formato);

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(repositorioObra.obtenerPorId(2L)).thenReturn(obra2);
        when(repositorioObra.obtenerPorId(3L)).thenReturn(obra3);
        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra1.getId(), formato)).thenReturn(formatoObra1);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra2.getId(), formato)).thenReturn(formatoObra2);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra3.getId(), formato)).thenReturn(formatoObra3);

        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra2.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra3.getId(), formato);

        List<Obra> resultado = servicioCarritoImpl.obtenerObras(usuario);

        assertThat(carrito.getItems().size(), is(equalTo(3)));
        assertThat(resultado.size(), is(3));
        assertThat(servicioCarritoImpl.contarItemsEnCarrito(usuario), is(3));
    }

    @Test
    public void debeVerificarQueNoSePuedaAgregarUnaMismaObraACarritoSiNoHayStock() throws NoHayStockSuficiente {
        Obra obra1 = new Obra();
        Formato formato = Formato.ORIGINAL;
        obra1.setId(1L);
        obra1.setStock(4);

        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);

        FormatoObra formatoObra = new FormatoObra();
        formatoObra.setStock(4);
        formatoObra.setFormato(formato);

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra1.getId(), formato)).thenReturn(formatoObra);

        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato);

        try {
            assertFalse(servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato));
        } catch (NoHayStockSuficiente e) {
        }

        List<Obra> resultado = servicioCarritoImpl.obtenerObras(usuario);
        assertThat(carrito.getItems().size(), is(1));
        assertThat(carrito.getItems().get(0).getCantidad(), is(4));
        assertThat(resultado.size(), is(1));
    }

    @Test
    public void seDebeVerificarQueSePuedaDisminuirLaCantidadDeUnaObraEnElCarrito() throws NoHayStockSuficiente {
        Obra obra1 = new Obra();
        Formato formato = Formato.ORIGINAL;
        obra1.setId(1L);
        obra1.setStock(4);
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);

        FormatoObra formatoObra = new FormatoObra();
        formatoObra.setStock(10);
        formatoObra.setFormato(formato);

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra1.getId(), formato)).thenReturn(formatoObra);

        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato);

        assertThat(carrito.getItems().get(0).getCantidad(), is(2));
        
        servicioCarritoImpl.disminuirCantidadDeObraDelCarrito(usuario, obra1.getId(), formato);
        
        List<Obra> resultado = servicioCarritoImpl.obtenerObras(usuario);
        assertThat(resultado.size(), is(1));
        assertThat(servicioCarritoImpl.contarItemsEnCarrito(usuario), is(1));
        assertThat(carrito.getItems().get(0).getCantidad(), is(1));
        assertThat(carrito.getItems().size(), is(1));
    }

    @Test
    public void vaciarCarrito_seDebeVerificarQueElCarritoSeVacieCorrectamente() throws NoHayStockSuficiente {
        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setStock(4);

        Obra obra2 = new Obra();
        obra2.setId(2L);
        obra2.setStock(4);

        Formato formato = Formato.ORIGINAL;
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);

        FormatoObra formatoObra1 = new FormatoObra();
        formatoObra1.setStock(10);
        formatoObra1.setFormato(formato);
        
        FormatoObra formatoObra2 = new FormatoObra();
        formatoObra2.setStock(10);
        formatoObra2.setFormato(formato);

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(repositorioObra.obtenerPorId(2L)).thenReturn(obra2);
        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra1.getId(), formato)).thenReturn(formatoObra1);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra2.getId(), formato)).thenReturn(formatoObra2);

        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra2.getId(), formato);
        servicioCarritoImpl.vaciarCarrito(usuario);

        assertThat(servicioCarritoImpl.contarItemsEnCarrito(usuario),is(equalTo(0)));
        assertThat(carrito.getItems().size(), is(0));
    }

    @Test
    public void obtenerCarritoConItems_debeObtenerCarritoConItemsDeUsuarioEspecifico() throws NoHayStockSuficiente {
        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setStock(2);

        Obra obra2 = new Obra();
        obra2.setId(2L);
        obra2.setStock(2);

        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setEmail("email1@test.com");
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setEmail("email2@test.com");

        Carrito carrito1 = new Carrito(usuario1);
        carrito1.setId(1L);
        Carrito carrito2 = new Carrito(usuario2);

        Formato formato = Formato.ORIGINAL;

        FormatoObra formatoObra1 = new FormatoObra();
        formatoObra1.setStock(10);
        formatoObra1.setFormato(formato);
        
        FormatoObra formatoObra2 = new FormatoObra();
        formatoObra2.setStock(10);
        formatoObra2.setFormato(formato);

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(repositorioObra.obtenerPorId(2L)).thenReturn(obra2);

        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario1.getId())).thenReturn(carrito1);
        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario2.getId())).thenReturn(carrito2);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra1.getId(), formato)).thenReturn(formatoObra1);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra2.getId(), formato)).thenReturn(formatoObra2);

        servicioCarritoImpl.agregarObraAlCarrito(usuario1, obra1.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario1, obra1.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario1, obra2.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario2, obra2.getId(), formato);

        assertThat(servicioCarritoImpl.obtenerCarritoConItems(usuario1),is(equalTo(carrito1)));
        assertThat(servicioCarritoImpl.contarItemsEnCarrito(usuario1),is(equalTo(3)));
        assertThat(servicioCarritoImpl.obtenerCarritoConItems(usuario2),is(equalTo(carrito2)));
        assertThat(servicioCarritoImpl.contarItemsEnCarrito(usuario2),is(equalTo(1)));
    }


    @Test
    public void dadoQueTengoTresObrasEnCarritoDosDe$3500YUnaDe$5200DebeDevolver$12200() throws NoHayStockSuficiente {
        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setStock(2);

        Obra obra2 = new Obra();
        obra2.setId(2L);
        obra2.setStock(2);

        Obra obra3 = new Obra();
        obra3.setId(3L);
        obra3.setStock(2);

        Formato formato = Formato.ORIGINAL;
        FormatoObra formatoObra1 = new FormatoObra();
        formatoObra1.setStock(10);
        formatoObra1.setFormato(formato);
        formatoObra1.setPrecio(3500.0);
        
        FormatoObra formatoObra2 = new FormatoObra();
        formatoObra2.setStock(10);
        formatoObra2.setFormato(formato);
        formatoObra2.setPrecio(3500.0);
        
        FormatoObra formatoObra3 = new FormatoObra();
        formatoObra3.setStock(10);
        formatoObra3.setFormato(formato);
        formatoObra3.setPrecio(5200.0);
        
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        
        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(repositorioObra.obtenerPorId(2L)).thenReturn(obra2);
        when(repositorioObra.obtenerPorId(3L)).thenReturn(obra3);
        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra1.getId(), formato)).thenReturn(formatoObra1);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra2.getId(), formato)).thenReturn(formatoObra2);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra3.getId(), formato)).thenReturn(formatoObra3);

        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra2.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra3.getId(), formato);

        assertThat(servicioCarritoImpl.calcularPrecioTotalCarrito(usuario),is(equalTo(12200.0)));
    }

    @Test
    public void dadoQueNoHayObrasEnCarritoElPrecioTotalDebeSer0(){
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);

        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);

        assertThat(servicioCarritoImpl.calcularPrecioTotalCarrito(usuario),is(equalTo(0.0)));
    }


   @Test
   public void contarCorrectamenteTotalDeItemsEnElCarrito() throws NoHayStockSuficiente {
        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setStock(2);

        Obra obra2 = new Obra();
        obra2.setStock(2);
        obra2.setId(2L);

        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);

        when(repositorioObra.obtenerPorId(1L)).thenReturn(obra1);
        when(repositorioObra.obtenerPorId(2L)).thenReturn(obra2);

        when(repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId())).thenReturn(carrito);

        Formato formato = Formato.ORIGINAL;
        
        FormatoObra formatoObra1 = new FormatoObra();
        formatoObra1.setStock(10);
        formatoObra1.setFormato(formato);
        
        FormatoObra formatoObra2 = new FormatoObra();
        formatoObra2.setStock(10);
        formatoObra2.setFormato(formato);

        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra1.getId(), formato)).thenReturn(formatoObra1);
        when(repositorioFormatoObra.obtenerFormatoPorObraYFormato(obra2.getId(), formato)).thenReturn(formatoObra2);

        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra1.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra2.getId(), formato);
        servicioCarritoImpl.agregarObraAlCarrito(usuario, obra2.getId(), formato);

        assertThat(servicioCarritoImpl.contarItemsEnCarrito(usuario), is(equalTo(3)));
   }

}
