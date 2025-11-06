package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.enums.EstadoPago;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.repositorios.RepositorioCompraHecha;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
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
        obra.setPrecio(0.0);
        carrito.agregarItem(obra);

        ItemCarrito itemCarrito = carrito.getItems().get(0);
        ItemCompra itemCompra = new ItemCompra(itemCarrito);

        CompraHecha orden = new CompraHecha(1L, carrito, carrito.getTotal(), usuario);
        itemCompra.setCompra(orden);
        orden.setItems(List.of(itemCompra));

        repositorioOrden.guardar(orden);
        CompraHecha ordenObtenida = repositorioOrden.obtenerPorId(orden.getId());

        assertThat(ordenObtenida, is(equalTo(orden)));
        assertEquals(orden.getId(), ordenObtenida.getId());
        assertEquals(orden.getItems().size(), ordenObtenida.getItems().size());
    }


    @Test
    public void deberiaEliminarUnaOrdenDeCompraCorrectamente (){
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Carrito carrito2 = new Carrito(usuario);

        Obra obra1 = new Obra();
        Obra obra2 = new Obra();

        obra1.setPrecio(100.0);
        carrito.agregarItem(obra1);
        obra2.setPrecio(100.0);
        carrito2.agregarItem(obra2);

        ItemCarrito itemCarrito = carrito.getItems().get(0);
        ItemCompra itemCompra = new ItemCompra(itemCarrito);
        ItemCarrito itemCarrito2 = carrito2.getItems().get(0);
        ItemCompra itemCompra2 = new ItemCompra(itemCarrito2);

        CompraHecha orden = new CompraHecha(1L, carrito, carrito.getTotal(), usuario);
        CompraHecha orden2 = new CompraHecha(2L, carrito2, carrito2.getTotal(), usuario);

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
    public void deberiaObtenerOrdenesDeCompraDeUsuarioCorrectamente (){

            Usuario usuario = new Usuario();
            Usuario usuario2 = new Usuario();
            Carrito carrito = new Carrito(usuario);
            Carrito carrito2 = new Carrito(usuario);
            Carrito carrito3 = new Carrito(usuario2);

            Obra obra1 = new Obra();
            Obra obra2 = new Obra();
            Obra obra3 = new Obra();

            obra1.setPrecio(100.0);
            carrito.agregarItem(obra1);
            obra2.setPrecio(100.0);
            carrito2.agregarItem(obra2);
            obra3.setPrecio(100.0);
            carrito3.agregarItem(obra3);

            ItemCarrito itemCarrito = carrito.getItems().get(0);
            ItemCompra itemCompra = new ItemCompra(itemCarrito);
            ItemCarrito itemCarrito2 = carrito2.getItems().get(0);
            ItemCompra itemCompra2 = new ItemCompra(itemCarrito2);
            ItemCarrito itemCarrito3 = carrito3.getItems().get(0);
            ItemCompra itemCompra3 = new ItemCompra(itemCarrito3);

            CompraHecha orden = new CompraHecha(1L, carrito, carrito.getTotal(), usuario);
            CompraHecha orden2 = new CompraHecha(2L, carrito2, carrito2.getTotal(), usuario);
            CompraHecha orden3 = new CompraHecha(3L, carrito3, carrito3.getTotal(), usuario2);

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
    @Test
    public void deberiaActualizarElEstadoDeLaOrdenDeCompraCorrectamente (){
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Carrito carrito2 = new Carrito(usuario);

        Obra obra1 = new Obra();
        Obra obra2 = new Obra();


        obra1.setPrecio(100.0);
        carrito.agregarItem(obra1);
        obra2.setPrecio(100.0);
        carrito2.agregarItem(obra2);


        ItemCarrito itemCarrito = carrito.getItems().get(0);
        ItemCompra itemCompra = new ItemCompra(itemCarrito);
        ItemCarrito itemCarrito2 = carrito2.getItems().get(0);
        ItemCompra itemCompra2 = new ItemCompra(itemCarrito2);


        CompraHecha orden1 = new CompraHecha(1L, carrito, carrito.getTotal(), usuario);
        CompraHecha orden2 = new CompraHecha(2L, carrito2, carrito2.getTotal(), usuario);

        itemCompra.setCompra(orden1);
        orden1.setItems(List.of(itemCompra));
        itemCompra2.setCompra(orden2);
        orden2.setItems(List.of(itemCompra2));

        orden1.setEstadoPago(EstadoPago.PENDIENTE);
        orden2.setEstadoPago(EstadoPago.PENDIENTE);

        repositorioUsuario.guardar(usuario);
        repositorioCarrito.guardar(carrito);
        repositorioCarrito.guardar(carrito2);
        repositorioObra.guardar(obra1);
        repositorioObra.guardar(obra2);

        repositorioOrden.guardar(orden1);
        repositorioOrden.guardar(orden2);


        CompraHecha ordenEstado1 = repositorioOrden.obtenerPorId(orden1.getId());
        CompraHecha ordenEstado2 = repositorioOrden.obtenerPorId(orden2.getId());

        assertThat(ordenEstado1.getEstadoPago(), is(EstadoPago.PENDIENTE));
        assertThat(ordenEstado2.getEstadoPago(), is(EstadoPago.PENDIENTE));

        repositorioOrden.actualizarEstado(orden1.getId(), EstadoPago.APROBADO);
        repositorioOrden.actualizarEstado(orden2.getId(), EstadoPago.RECHAZADO);


        CompraHecha ordenEstadoActualizado1 = repositorioOrden.obtenerPorId(orden1.getId());
        CompraHecha ordenEstadoActualizado2 = repositorioOrden.obtenerPorId(orden2.getId());

        assertThat(ordenEstadoActualizado1.getEstadoPago(), is(EstadoPago.APROBADO));
        assertThat(ordenEstadoActualizado2.getEstadoPago(), is(EstadoPago.RECHAZADO));

    }

}
