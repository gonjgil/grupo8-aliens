package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.List;

import com.tallerwebi.dominio.entidades.Artista;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
public class RepositorioObraImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioObra repositorioObra;

    @BeforeEach
    public void init() {
        this.repositorioObra = new RepositorioObraImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaGuardarUnaObra() {
        Artista artista = persistirArtista("Artista de la Obra Guardada");

        Obra obra = generarObra1();
        obra.setTitulo("Titulo de la Obra Guardada");
        obra.setArtista(artista);
        
        this.repositorioObra.guardar(obra);

        String hql = "FROM Obra WHERE titulo = :titulo";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("titulo", "Titulo de la Obra Guardada");

        Obra obraObtenida = (Obra) query.getSingleResult();

        assertThat(obraObtenida, is(equalTo(obra)));
    }

    @Test
    @Transactional
    @Rollback
    public void alListarTodasLasObrasDeberiaObtenerLaListaCompleta() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        String hql = "FROM Obra";
        TypedQuery<Obra> query = this.sessionFactory.getCurrentSession().createQuery(hql, Obra.class);
        List<Obra> obrasObtenidas = query.getResultList();

        assertThat(obrasObtenidas.size(), is(equalTo(3)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra1)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra2)));
        assertThat(obrasObtenidas.get(2), is(equalTo(obra3)));
    }

    @Test
    @Transactional
    @Rollback
    public void alListarObrasPorCategoriaYNoExistirNingunaDeberiaObtenerUnaListaVacia() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.agregarCategoria(Categoria.ABSTRACTO);
        obra2.agregarCategoria(Categoria.SURREALISMO);
        obra3.agregarCategoria(Categoria.RETRATO);

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        String hql = "FROM Obra WHERE :categoria MEMBER OF categorias";
        TypedQuery<Obra> query = this.sessionFactory.getCurrentSession().createQuery(hql, Obra.class);
        query.setParameter("categoria", Categoria.TEST);
        List<Obra> obrasObtenidas = query.getResultList();

        assertThat(obrasObtenidas.size(), is(equalTo(0)));
    }

    @Test
    @Transactional
    @Rollback
    public void alListarObrasPorAutorDeberiaObtenerSoloTodasSusObras() {
        Artista artista = persistirArtista("Autor de dos Obras");
        Artista artistaOtro = persistirArtista("Autor de una Obra");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.setArtista(artista);
        obra2.setArtista(artistaOtro);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        String hql = "SELECT o FROM Obra o LEFT JOIN o.artista a WHERE a.nombre = :autor";
        TypedQuery<Obra> query = this.sessionFactory.getCurrentSession().createQuery(hql, Obra.class);
        query.setParameter("autor", "Autor de dos Obras");
        List<Obra> obrasObtenidas = query.getResultList();

        assertThat(obrasObtenidas.size(), is(equalTo(2)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra1)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra3)));
    }
    
    @Test
    @Transactional
    @Rollback
    public void alListarObrasPorCategoriaDeberiaObtenerSoloTodasLasObrasDeEsaCategoria() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.agregarCategoria(Categoria.ABSTRACTO);
        obra3.agregarCategoria(Categoria.ABSTRACTO);

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        String hql = "FROM Obra WHERE :categoria MEMBER OF categorias";
        TypedQuery<Obra> query = this.sessionFactory.getCurrentSession().createQuery(hql, Obra.class);
        query.setParameter("categoria", Categoria.ABSTRACTO);
        List<Obra> obrasObtenidas = query.getResultList();

        assertThat(obrasObtenidas.size(), is(equalTo(2)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra1)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra3)));
    }

    @Test
    @Transactional
    @Rollback
    public void alBuscarObraPorIdDeberiaObtenerLaObraCorrespondiente() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();

        obra1.setArtista(artista);

        this.repositorioObra.guardar(obra1);

        String hql = "FROM Obra WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", obra1.getId());
        Obra obraObtenida = (Obra) query.getSingleResult();

        assertThat(obraObtenida, is(equalTo(obra1)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorTituloObtengaLasObrasCorrespondientes() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.setTitulo("Obra Especial");
        obra2.setTitulo("Otra Obra Especial");
        obra3.setTitulo("Obra Común");
        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.buscarPorString("Especial");

        assertThat(obrasObtenidas.size(), is(equalTo(2)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra1)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra2)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorRangoDePrecioObtengaLasObrasCorrespondientes() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        
        // Agregar formatos con precios específicos para el test
        FormatoObra formato1 = new FormatoObra(obra1, Formato.ORIGINAL, 1500.0, 5);
        FormatoObra formato2 = new FormatoObra(obra2, Formato.ORIGINAL, 2500.0, 5);
        FormatoObra formato3 = new FormatoObra(obra3, Formato.ORIGINAL, 3500.0, 5);
        
        obra1.agregarFormato(formato1);
        obra2.agregarFormato(formato2);
        obra3.agregarFormato(formato3);

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.obtenerPorRangoDePrecio(2000.0, 4000.0);

        assertThat(obrasObtenidas.size(), is(equalTo(2)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra2)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra3)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorDescripcionObtengaLasObrasCorrespondientes() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.setDescripcion("Esta es una obra maravillosa");
        obra2.setDescripcion("Una obra que destaca por su belleza");
        obra3.setDescripcion("Obra común sin nada especial");
        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.buscarPorString("obra");

        assertThat(obrasObtenidas.size(), is(equalTo(3)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra1)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra2)));
        assertThat(obrasObtenidas.get(2), is(equalTo(obra3)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorDescripcionYNoEncuentreCoincidenciasDevuelvaUnaListaVacia() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.setDescripcion("Esta es una obra maravillosa");
        obra2.setDescripcion("Una obra que destaca por su belleza");
        obra3.setDescripcion("Obra común sin nada especial");
        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.buscarPorString("inexistente");

        assertThat(obrasObtenidas.size(), is(equalTo(0)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorRangoDePrecioSinCoincidenciasDevuelvaUnaListaVacia() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        
        // Agregar formatos con precios fuera del rango de búsqueda (4000-5000)
        FormatoObra formato1 = new FormatoObra(obra1, Formato.ORIGINAL, 1500.0, 5);
        FormatoObra formato2 = new FormatoObra(obra2, Formato.ORIGINAL, 2500.0, 5);
        FormatoObra formato3 = new FormatoObra(obra3, Formato.ORIGINAL, 3500.0, 5);
        
        obra1.agregarFormato(formato1);
        obra2.agregarFormato(formato2);
        obra3.agregarFormato(formato3);

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.obtenerPorRangoDePrecio(4000.0, 5000.0);

        assertThat(obrasObtenidas.size(), is(equalTo(0)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorTituloSinCoincidenciasDevuelvaUnaListaVacia() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.buscarPorString("Inexistente");

        assertThat(obrasObtenidas.size(), is(equalTo(0)));
    }

    private Obra generarObra1() {
        Artista artista = new Artista();
        artista.setNombre("Artista de la Obra 1");
        Obra obra1 = new Obra();
        obra1.setTitulo("Titulo de la Obra 1");
        obra1.setArtista(artista);
        obra1.setDescripcion("Descripcion de la Obra 1");
        obra1.setImagenUrl("http://imagen.com/obra1.jpg");
        obra1.agregarCategoria(Categoria.ABSTRACTO);
        return obra1;
    }
    
    private Obra generarObra2() {
        Artista artista = new Artista();
        artista.setNombre("Artista de la Obra 2");
        Obra obra2 = new Obra();
        obra2.setTitulo("Titulo de la Obra 2");
        obra2.setArtista(artista);
        obra2.setDescripcion("Descripcion de la Obra 2");
        obra2.setImagenUrl("http://imagen.com/obra2.jpg");
        obra2.agregarCategoria(Categoria.SURREALISMO);
        return obra2;
    }

    private Obra generarObra3() {
        Artista artista = new Artista();
        artista.setNombre("Autor de la obra 3");
        Obra obra3 = new Obra();
        obra3.setTitulo("Titulo de la Obra 3");
        obra3.setArtista(artista);
        obra3.setDescripcion("Descripcion de la Obra 3");
        obra3.setImagenUrl("http://imagen.com/obra3.jpg");
        obra3.agregarCategoria(Categoria.ABSTRACTO);
        return obra3;
    }

    private Artista persistirArtista(String nombre) {
        Artista a = new Artista();
        a.setNombre(nombre);
        this.sessionFactory.getCurrentSession().save(a);
        return a;
    }

    @Test
    @Transactional
    @Rollback
    void queSePuedaEliminarUnaObraExistente() {
        Artista artista = new Artista();
        artista.setNombre("Artista");

        Obra obra = new Obra();
        obra.setTitulo("Obra para eliminar");
        obra.setArtista(artista);
        repositorioObra.guardar(obra);

        repositorioObra.eliminar(obra);

        Obra obraEliminada = sessionFactory.getCurrentSession().get(Obra.class, obra.getId());
        assertThat(obraEliminada, is(nullValue()));
    }
}
