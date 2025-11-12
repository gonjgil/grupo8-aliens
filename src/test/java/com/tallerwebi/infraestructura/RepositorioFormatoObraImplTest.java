package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.repositorios.RepositorioFormatoObra;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
public class RepositorioFormatoObraImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioFormatoObra repositorioFormato;

    @BeforeEach
    public void init() {
        this.repositorioFormato = new RepositorioFormatoObraImpl(this.sessionFactory);
    }

    private Obra persistirObraDePrueba() {
        Obra obra = new Obra();
        obra.setTitulo("Obra de Prueba");
        obra.setDescripcion("Descripción de la obra de prueba");
        sessionFactory.getCurrentSession().save(obra);
        return obra;
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnFormato() {
        FormatoObra formatoObra = new FormatoObra();
        formatoObra.setFormato(Formato.ORIGINAL);
        formatoObra.setPrecio(1500.0);
        formatoObra.setStock(5);
        formatoObra.setObra(persistirObraDePrueba());

        repositorioFormato.guardar(formatoObra);

        assertThat(repositorioFormato.obtenerTodos().size(), is(equalTo(1)));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnFormatoPorId() {
        FormatoObra formatoObra = new FormatoObra();
        formatoObra.setFormato(Formato.IMPRESION_CANVAS);
        formatoObra.setPrecio(300.0);
        formatoObra.setStock(10);
        formatoObra.setObra(persistirObraDePrueba());

        sessionFactory.getCurrentSession().save(formatoObra);

        FormatoObra formatoObraRecuperado = repositorioFormato.obtenerPorId(formatoObra.getId());

        assertThat(formatoObraRecuperado.getFormato(), is(equalTo(Formato.IMPRESION_CANVAS)));
        assertThat(formatoObraRecuperado.getPrecio(), is(equalTo(300.0)));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerFormatoPorObraYFormato() {
        Obra obra = persistirObraDePrueba();

        FormatoObra formatoObra = new FormatoObra();
        formatoObra.setFormato(Formato.DIGITAL);
        formatoObra.setPrecio(100.0);
        formatoObra.setStock(20);
        formatoObra.setObra(obra);

        sessionFactory.getCurrentSession().save(formatoObra);

        FormatoObra formatoObraRecuperado = repositorioFormato.obtenerFormatoPorObraYFormato(obra.getId(), Formato.DIGITAL);

        assertThat(formatoObraRecuperado.getFormato(), is(equalTo(Formato.DIGITAL)));
        assertThat(formatoObraRecuperado.getPrecio(), is(equalTo(100.0)));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerFormatosPorObra() {
        Obra obra = persistirObraDePrueba();

        FormatoObra formato1 = new FormatoObra();
        formato1.setFormato(Formato.ORIGINAL);
        formato1.setPrecio(1500.0);
        formato1.setStock(5);
        formato1.setObra(obra);

        FormatoObra formato2 = new FormatoObra();
        formato2.setFormato(Formato.IMPRESION_CANVAS);
        formato2.setPrecio(300.0);
        formato2.setStock(10);
        formato2.setObra(obra);

        sessionFactory.getCurrentSession().save(formato1);
        sessionFactory.getCurrentSession().save(formato2);

        var formatosRecuperados = repositorioFormato.obtenerFormatosPorObra(obra.getId());

        assertThat(formatosRecuperados.size(), is(equalTo(2)));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaEliminarUnFormato() {
        FormatoObra formatoObra = new FormatoObra();
        formatoObra.setFormato(Formato.ORIGINAL);
        formatoObra.setPrecio(1500.0);
        formatoObra.setStock(5);
        formatoObra.setObra(persistirObraDePrueba());

        sessionFactory.getCurrentSession().save(formatoObra);

        repositorioFormato.eliminar(formatoObra);

        assertThat(repositorioFormato.obtenerTodos().size(), is(equalTo(0)));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerTodosLosFormatos() {
        FormatoObra formato1 = new FormatoObra();
        formato1.setFormato(Formato.ORIGINAL);
        formato1.setPrecio(1500.0);
        formato1.setStock(5);
        formato1.setObra(persistirObraDePrueba());

        FormatoObra formato2 = new FormatoObra();
        formato2.setFormato(Formato.IMPRESION_CANVAS);
        formato2.setPrecio(300.0);
        formato2.setStock(10);
        formato2.setObra(persistirObraDePrueba());

        sessionFactory.getCurrentSession().save(formato1);
        sessionFactory.getCurrentSession().save(formato2);

        var todosLosFormatos = repositorioFormato.obtenerTodos();

        assertThat(todosLosFormatos.size(), is(equalTo(2)));
    }

    @Test
    @Transactional
    @Rollback
    public void queObtenerFormatoPorObraYFormatoRetorneNullSiNoExiste() {
        Obra obra = persistirObraDePrueba();
        FormatoObra formatoObraRecuperado = repositorioFormato.obtenerFormatoPorObraYFormato(obra.getId(), Formato.DIGITAL);

        assertThat(formatoObraRecuperado, is(equalTo(null)));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaActualizarElPrecioDeUnFormato() {
        // Arrange
        FormatoObra formato = new FormatoObra();
        formato.setFormato(Formato.ORIGINAL);
        formato.setPrecio(1000.0);
        formato.setStock(5);
        formato.setObra(persistirObraDePrueba());
        sessionFactory.getCurrentSession().save(formato);

        repositorioFormato.actualizarPrecio(formato.getId(), 2000.0);

        FormatoObra formatoActualizado = repositorioFormato.obtenerPorId(formato.getId());
        assertThat(formatoActualizado.getPrecio(), is(equalTo(2000.0)));
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSeActualiceElPrecioSiElFormatoNoExiste() {
        repositorioFormato.actualizarPrecio(999L, 3000.0);

        assertThat(repositorioFormato.obtenerTodos().size(), is(equalTo(0)));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaActualizarElStockDeUnFormato() {
        FormatoObra formato = new FormatoObra();
        formato.setFormato(Formato.IMPRESION_CANVAS);
        formato.setPrecio(500.0);
        formato.setStock(10);
        formato.setObra(persistirObraDePrueba());
        sessionFactory.getCurrentSession().save(formato);

        repositorioFormato.actualizarStock(formato.getId(), 50);

        FormatoObra formatoActualizado = repositorioFormato.obtenerPorId(formato.getId());
        assertThat(formatoActualizado.getStock(), is(equalTo(50)));
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSeActualiceElStockSiElFormatoNoExiste() {
        repositorioFormato.actualizarStock(888L, 100);

        assertThat(repositorioFormato.obtenerTodos().size(), is(equalTo(0)));
    }

}
