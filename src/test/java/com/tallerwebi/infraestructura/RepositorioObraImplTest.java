package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.RepositorioObra;
import com.tallerwebi.dominio.enums.Categoria;
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
        Obra obra = generarObra1();
        obra.setTitulo("Titulo de la Obra Guardada");
        
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
        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();

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
        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.agregarCategoria(Categoria.ABSTRACTO);
        obra2.agregarCategoria(Categoria.SURREALISMO);
        obra3.agregarCategoria(Categoria.RETRATO);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        String hql = "FROM Obra WHERE :categoria MEMBER OF categorias";
        TypedQuery<Obra> query = this.sessionFactory.getCurrentSession().createQuery(hql, Obra.class);
        query.setParameter("categoria", Categoria.TEST);
        List<Obra> obrasObtenidas = query.getResultList();

        assertThat(obrasObtenidas.size(), is(equalTo(0)));
        assertThrows(IllegalArgumentException.class, () -> {
            query.setParameter("categoria", null);
            query.getResultList();
        }, "No se encontraron obras para la categoría especificada");
    }

    @Test
    @Transactional
    @Rollback
    public void alListarObrasPorAutorDeberiaObtenerSoloTodasSusObras() {
        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.setAutor("Autor de dos Obras");
        obra3.setAutor("Autor de dos Obras");

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        String hql = "FROM Obra WHERE autor = :autor";
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
        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.agregarCategoria(Categoria.ABSTRACTO);
        obra3.agregarCategoria(Categoria.ABSTRACTO);

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
        Obra obra1 = generarObra1();
     
        this.repositorioObra.guardar(obra1);    

        String hql = "FROM Obra WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", obra1.getId());
        Obra obraObtenida = (Obra) query.getSingleResult();

        assertThat(obraObtenida, is(equalTo(obra1)));
    }

    private Obra generarObra1() {
        Obra obra1 = new Obra();
        obra1.setTitulo("Titulo de la Obra 1");
        obra1.setAutor("Autor de la Obra 1");
        obra1.setDescripcion("Descripcion de la Obra 1");
        obra1.setImagenUrl("http://imagen.com/obra1.jpg");
        obra1.setPrecio(1500.0);
        obra1.agregarCategoria(Categoria.ABSTRACTO);
        return obra1;
    }
    
        private Obra generarObra2() {
            Obra obra2 = new Obra();
            obra2.setTitulo("Titulo de la Obra 2");
            obra2.setAutor("Autor de la Obra 2");
            obra2.setDescripcion("Descripcion de la Obra 2");
            obra2.setImagenUrl("http://imagen.com/obra2.jpg");
            obra2.setPrecio(2500.0);
            obra2.agregarCategoria(Categoria.SURREALISMO);
            return obra2;
        }

    private Obra generarObra3() {
        Obra obra3 = new Obra();
        obra3.setTitulo("Titulo de la Obra 3");
        obra3.setAutor("Autor de la Obra 3");
        obra3.setDescripcion("Descripcion de la Obra 3");
        obra3.setImagenUrl("http://imagen.com/obra3.jpg");
        obra3.setPrecio(3500.0);
        obra3.agregarCategoria(Categoria.ABSTRACTO);
        return obra3;
    }
    
}
