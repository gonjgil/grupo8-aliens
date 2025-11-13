package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.enums.EstadoPago;
import java.util.List;

import org.hibernate.SessionFactory;

import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.entidades.ItemCarrito;
import com.tallerwebi.dominio.entidades.ItemCompra;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.EstadoPago;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.repositorios.RepositorioCompraHecha;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
@Transactional
public class RepositorioCompraHechaImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioCompraHecha repositorioOrden;
    private RepositorioCarrito repositorioCarrito;

    private RepositorioObra repositorioObra;
    private RepositorioUsuario repositorioUsuario;

    @BeforeEach
    void setUp() {
        this.repositorioOrden = new RepositorioCompraHechaImpl(this.sessionFactory);
        this.repositorioCarrito = new RepositorioCarritoImpl(this.sessionFactory);
        this.repositorioObra = new RepositorioObraImpl(this.sessionFactory);
        this.repositorioUsuario = new RepositorioUsuarioImpl(this.sessionFactory);
    }


    @Test
    public void deberiaGuardarUnaOrdenDeCompraCorrectamente() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Obra obra = new Obra();

        // Crear formato para la obra
        FormatoObra formatoObra = new FormatoObra(obra, Formato.ORIGINAL, 2001.0, 5);
        obra.agregarFormato(formatoObra);

        carrito.agregarItem(obra, Formato.ORIGINAL, 2001.0);

        ItemCarrito itemCarrito = carrito.getItems().get(0);
        ItemCompra itemCompra = new ItemCompra(itemCarrito);

        CompraHecha orden = new CompraHecha(carrito, carrito.getTotal(), usuario);
        itemCompra.setCompra(orden);
        orden.setItems(List.of(itemCompra));

        CompraHecha ordenGuardada = repositorioOrden.guardar(orden);
        CompraHecha ordenObtenida = repositorioOrden.obtenerPorId(orden.getId());

        assertThat(ordenObtenida, is(equalTo(orden)));
        assertThat(ordenGuardada.getId(), is(equalTo(ordenObtenida.getId())));
        assertEquals(orden.getId(), ordenObtenida.getId());
        assertEquals(orden.getItems().size(), ordenObtenida.getItems().size());
    }


    @Test
    public void deberiaEliminarUnaOrdenDeCompraCorrectamente() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Carrito carrito2 = new Carrito(usuario);

        Obra obra1 = new Obra();
        FormatoObra formatoObra1 = new FormatoObra(obra1, Formato.ORIGINAL, 2001.0, 5);
        obra1.agregarFormato(formatoObra1);

        Obra obra2 = new Obra();
        FormatoObra formatoObra2 = new FormatoObra(obra2, Formato.ORIGINAL, 2001.0, 5);
        obra2.agregarFormato(formatoObra2);

        carrito.agregarItem(obra1, Formato.ORIGINAL, 2001.0);
        carrito2.agregarItem(obra2, Formato.ORIGINAL, 2001.0);

        ItemCarrito itemCarrito = carrito.getItems().get(0);
        ItemCompra itemCompra = new ItemCompra(itemCarrito);
        ItemCarrito itemCarrito2 = carrito2.getItems().get(0);
        ItemCompra itemCompra2 = new ItemCompra(itemCarrito2);

        CompraHecha orden = new CompraHecha(carrito, carrito.getTotal(), usuario);
        CompraHecha orden2 = new CompraHecha(carrito2, carrito2.getTotal(), usuario);

        itemCompra.setCompra(orden);
        orden.setItems(List.of(itemCompra));
        itemCompra2.setCompra(orden2);
        orden2.setItems(List.of(itemCompra2));

        repositorioUsuario.guardar(usuario);
        repositorioObra.guardar(obra1);
        repositorioObra.guardar(obra2);
        repositorioCarrito.guardar(carrito);
        repositorioCarrito.guardar(carrito2);

        repositorioOrden.guardar(orden);
        repositorioOrden.guardar(orden2);

        repositorioOrden.eliminar(orden);

        List<CompraHecha> ordenesObtenidas = repositorioOrden.obtenerTodasPorUsuario(usuario);
        CompraHecha ordenObtenida = repositorioOrden.obtenerPorId(orden2.getId());

        assertThat(ordenObtenida, is(orden2));
        assertThat(ordenesObtenidas.size(),is(1));
        assertThat(ordenesObtenidas.get(0).getId(), is(orden2.getId()));
    }

    @Test
    public void deberiaObtenerOrdenDeCompraSolicitadaPorIdCorrectamente() {
        CompraHecha orden = new CompraHecha();
        orden.setId(1L);

        repositorioOrden.guardar(orden);

        CompraHecha ordenObtenida = repositorioOrden.obtenerPorId(orden.getId());
        assertThat(ordenObtenida, is(orden));
    }

    @Test
    public void deberiaObtenerOrdenesDeCompraDeUsuarioCorrectamente() {

            Usuario usuario = new Usuario();
            Usuario usuario2 = new Usuario();
            Carrito carrito = new Carrito(usuario);
            Carrito carrito2 = new Carrito(usuario);
            Carrito carrito3 = new Carrito(usuario2);

            Obra obra1 = new Obra();
            Obra obra2 = new Obra();
            Obra obra3 = new Obra();

            carrito.agregarItem(obra1, Formato.ORIGINAL, 2001.0);
            carrito2.agregarItem(obra2, Formato.ORIGINAL, 2001.0);
            carrito3.agregarItem(obra3, Formato.ORIGINAL, 2001.0);

            ItemCarrito itemCarrito = carrito.getItems().get(0);
            ItemCompra itemCompra = new ItemCompra(itemCarrito);
            ItemCarrito itemCarrito2 = carrito2.getItems().get(0);
            ItemCompra itemCompra2 = new ItemCompra(itemCarrito2);
            ItemCarrito itemCarrito3 = carrito3.getItems().get(0);
            ItemCompra itemCompra3 = new ItemCompra(itemCarrito3);

            CompraHecha orden = new CompraHecha(carrito, carrito.getTotal(), usuario);
            CompraHecha orden2 = new CompraHecha(carrito2, carrito2.getTotal(), usuario);
            CompraHecha orden3 = new CompraHecha(carrito3, carrito3.getTotal(), usuario2);

            itemCompra.setCompra(orden);
            orden.setItems(List.of(itemCompra));
            itemCompra2.setCompra(orden2);
            orden2.setItems(List.of(itemCompra2));
            itemCompra3.setCompra(orden3);
            orden3.setItems(List.of(itemCompra3));

            repositorioUsuario.guardar(usuario);
            repositorioUsuario.guardar(usuario2);
            repositorioObra.guardar(obra1);
            repositorioObra.guardar(obra2);
            repositorioObra.guardar(obra3);
            repositorioCarrito.guardar(carrito);
            repositorioCarrito.guardar(carrito2);
            repositorioCarrito.guardar(carrito3);

            repositorioOrden.guardar(orden);
            repositorioOrden.guardar(orden2);
            repositorioOrden.guardar(orden3);


            List<CompraHecha> ordenesDeUsuario = repositorioOrden.obtenerTodasPorUsuario(usuario);

            assertThat(ordenesDeUsuario.size(),is(2));
            assertThat(ordenesDeUsuario.get(0).getId(), is(orden.getId()));
            assertThat(ordenesDeUsuario.get(1).getId(), is(orden2.getId()));
            assertThat(orden.getUsuario(), is(usuario));


            List<CompraHecha> ordenesDeUsuario2 = repositorioOrden.obtenerTodasPorUsuario(usuario2);

            assertThat(ordenesDeUsuario2.size(),is(1));
            assertThat(ordenesDeUsuario2.get(0).getId(), is(orden3.getId()));
            assertThat(orden3.getUsuario(), is(usuario2));
    }
}
