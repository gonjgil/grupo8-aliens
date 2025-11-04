package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.enums.EstadoOrdenCompra;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.repositorios.RepositorioOrdenCompra;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
@Transactional
public class RepositorioOrdenCompraImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioOrdenCompra repositorioOrden;
    private RepositorioCarrito repositorioCarrito;

    private RepositorioObra repositorioObra;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioFormatoObra repositorioFormatoObra;

    @BeforeEach
    void setUp() {
        this.repositorioOrden = new RepositorioOrdenCompraImpl(this.sessionFactory);
        this.repositorioCarrito = new RepositorioCarritoImpl(this.sessionFactory);
        this.repositorioObra = new RepositorioObraImpl(this.sessionFactory);
        this.repositorioUsuario = new RepositorioUsuarioImpl(this.sessionFactory);
        this.repositorioFormatoObra = new RepositorioFormatoObraImpl(this.sessionFactory);
    }

    @Test
    public void deberiaCrearUnaOrdenDeCompraCorrectamente() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Obra obra = new Obra();
        obra.setPrecio(2001.0);
        carrito.agregarItem(obra, Formato.ORIGINAL, 2001.0);

        ItemCarrito itemCarrito = carrito.getItems().get(0);
        ItemOrden itemOrden = new ItemOrden(itemCarrito);

        OrdenCompra orden = new OrdenCompra(1L, carrito, carrito.getTotal(), usuario);
        itemOrden.setOrden(orden);
        orden.setItems(List.of(itemOrden));

        Boolean ordenCreada = repositorioOrden.crearOrdenCompra(orden);
        assertThat(ordenCreada, is(true));
        OrdenCompra ordenObtenida = repositorioOrden.obtenerPorId(orden.getId());
        assertThat(ordenObtenida, is(equalTo(orden)));
    }

    @Test
    public void deberiaGuardarUnaOrdenDeCompraCorrectamente() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Obra obra = new Obra();
        obra.setPrecio(2001.0);
        carrito.agregarItem(obra, Formato.ORIGINAL, 2001.0);

        ItemCarrito itemCarrito = carrito.getItems().get(0);
        ItemOrden itemOrden = new ItemOrden(itemCarrito);

        OrdenCompra orden = new OrdenCompra(1L, carrito, carrito.getTotal(), usuario);
        itemOrden.setOrden(orden);
        orden.setItems(List.of(itemOrden));

        repositorioOrden.guardar(orden);
        OrdenCompra ordenObtenida = repositorioOrden.obtenerPorId(orden.getId());

        assertThat(ordenObtenida, is(equalTo(orden)));
        assertEquals(orden.getId(), ordenObtenida.getId());
        assertEquals(orden.getItems().size(), ordenObtenida.getItems().size());
    }


    @Test
    public void deberiaEliminarUnaOrdenDeCompraCorrectamente() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Carrito carrito2 = new Carrito(usuario);

        Obra obra1 = new Obra();
        obra1.setPrecio(2001.0);
        Obra obra2 = new Obra();
        obra2.setPrecio(2001.0);

        carrito.agregarItem(obra1, Formato.ORIGINAL, 2001.0);
        carrito2.agregarItem(obra2, Formato.ORIGINAL, 2001.0);

        ItemCarrito itemCarrito = carrito.getItems().get(0);
        ItemOrden itemOrden = new ItemOrden(itemCarrito);
        ItemCarrito itemCarrito2 = carrito2.getItems().get(0);
        ItemOrden itemOrden2 = new ItemOrden(itemCarrito2);

        OrdenCompra orden = new OrdenCompra(1L, carrito, carrito.getTotal(), usuario);
        OrdenCompra orden2 = new OrdenCompra(2L, carrito2, carrito2.getTotal(), usuario);

        itemOrden.setOrden(orden);
        orden.setItems(List.of(itemOrden));
        itemOrden2.setOrden(orden2);
        orden2.setItems(List.of(itemOrden2));

        repositorioUsuario.guardar(usuario);
        repositorioObra.guardar(obra1);
        repositorioObra.guardar(obra2);
        repositorioCarrito.guardar(carrito);
        repositorioCarrito.guardar(carrito2);

        repositorioOrden.guardar(orden);
        repositorioOrden.guardar(orden2);

        repositorioOrden.eliminar(orden);

        List<OrdenCompra> ordenesObtenidas = repositorioOrden.obtenerTodasPorUsuario(usuario);
        OrdenCompra ordenObtenida = repositorioOrden.obtenerPorId(orden2.getId());

        assertThat(ordenObtenida, is(orden2));
        assertThat(ordenesObtenidas.size(),is(1));
        assertThat(ordenesObtenidas.get(0).getId(), is(orden2.getId()));
    }

    @Test
    public void deberiaObtenerOrdenDeCompraSolicitadaPorIdCorrectamente() {
        OrdenCompra orden = new OrdenCompra();
        orden.setId(1L);

        repositorioOrden.crearOrdenCompra(orden);
        repositorioOrden.guardar(orden);

        OrdenCompra ordenObtenida = repositorioOrden.obtenerPorId(orden.getId());
        assertThat(ordenObtenida, is(orden));
    }

    @Test
    public void deberiaObtenerOrdenCompraSolicitadaPorEstadoDeCompraCorrectamente() {
        OrdenCompra orden1 = new OrdenCompra();
        OrdenCompra orden2 = new OrdenCompra();
        OrdenCompra orden3 = new OrdenCompra();
        OrdenCompra orden4 = new OrdenCompra();
        OrdenCompra orden5 = new OrdenCompra();
        OrdenCompra orden6 = new OrdenCompra();
        OrdenCompra orden7 = new OrdenCompra();


        orden1.setEstado(EstadoOrdenCompra.PENDIENTE);
        orden2.setEstado(EstadoOrdenCompra.PENDIENTE);

        orden3.setEstado(EstadoOrdenCompra.RECHAZADA);
        orden4.setEstado(EstadoOrdenCompra.RECHAZADA);

        orden5.setEstado(EstadoOrdenCompra.ACEPTADA);
        orden6.setEstado(EstadoOrdenCompra.ACEPTADA);
        orden7.setEstado(EstadoOrdenCompra.ACEPTADA);


        repositorioOrden.guardar(orden1);
        repositorioOrden.guardar(orden2);
        repositorioOrden.guardar(orden3);
        repositorioOrden.guardar(orden4);
        repositorioOrden.guardar(orden5);
        repositorioOrden.guardar(orden6);
        repositorioOrden.guardar(orden7);


        List<OrdenCompra> ordenesObtenidas = repositorioOrden.obtenerPorEstado(EstadoOrdenCompra.ACEPTADA);

        assertThat(ordenesObtenidas.size(),is(3));
        assertThat(ordenesObtenidas.get(0).getId(), is(orden5.getId()));
        assertThat(ordenesObtenidas.get(1).getId(), is(orden6.getId()));
        assertThat(ordenesObtenidas.get(2).getId(), is(orden7.getId()));


    }

    @Test
    public void deberiaObtenerOrdenesDeCompraDeUsuarioCorrectamente() {

            Usuario usuario = new Usuario();
            Usuario usuario2 = new Usuario();
            Carrito carrito = new Carrito(usuario);
            Carrito carrito2 = new Carrito(usuario);
            Carrito carrito3 = new Carrito(usuario2);

            Obra obra1 = new Obra();
            obra1.setPrecio(2001.0);
            Obra obra2 = new Obra();
            obra2.setPrecio(2001.0);
            Obra obra3 = new Obra();
            obra3.setPrecio(2001.0);

            carrito.agregarItem(obra1, Formato.ORIGINAL, 2001.0);
            carrito2.agregarItem(obra2, Formato.ORIGINAL, 2001.0);
            carrito3.agregarItem(obra3, Formato.ORIGINAL, 2001.0);

            ItemCarrito itemCarrito = carrito.getItems().get(0);
            ItemOrden itemOrden = new ItemOrden(itemCarrito);
            ItemCarrito itemCarrito2 = carrito2.getItems().get(0);
            ItemOrden itemOrden2 = new ItemOrden(itemCarrito2);
            ItemCarrito itemCarrito3 = carrito3.getItems().get(0);
            ItemOrden itemOrden3 = new ItemOrden(itemCarrito3);

            OrdenCompra orden = new OrdenCompra(1L, carrito, carrito.getTotal(), usuario);
            OrdenCompra orden2 = new OrdenCompra(2L, carrito2, carrito2.getTotal(), usuario);
            OrdenCompra orden3 = new OrdenCompra(3L, carrito3, carrito3.getTotal(), usuario2);

            itemOrden.setOrden(orden);
            orden.setItems(List.of(itemOrden));
            itemOrden2.setOrden(orden2);
            orden2.setItems(List.of(itemOrden2));
            itemOrden3.setOrden(orden3);
            orden3.setItems(List.of(itemOrden3));

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


            List<OrdenCompra> ordenesDeUsuario = repositorioOrden.obtenerTodasPorUsuario(usuario);

            assertThat(ordenesDeUsuario.size(),is(2));
            assertThat(ordenesDeUsuario.get(0).getId(), is(orden.getId()));
            assertThat(ordenesDeUsuario.get(1).getId(), is(orden2.getId()));
            assertThat(orden.getUsuario(), is(usuario));


            List<OrdenCompra> ordenesDeUsuario2 = repositorioOrden.obtenerTodasPorUsuario(usuario2);

            assertThat(ordenesDeUsuario2.size(),is(1));
            assertThat(ordenesDeUsuario2.get(0).getId(), is(orden3.getId()));
            assertThat(orden3.getUsuario(), is(usuario2));
    }
}
