package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import javax.persistence.Query;
import javax.transaction.Transactional;

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

        Obra Obra = new Obra();
        Obra.setTitulo("Titulo de la Obra Testeada");
        Obra.setAutor("Autor de la Obra");
        Obra.setDescripcion("Descripcion de la Obra");
        Obra.setImagenUrl("http://imagen.com/obra.jpg");
        Obra.setPrecio(1500.0);
        
        this.repositorioObra.guardar(Obra);

        String hql = "FROM Obra WHERE titulo = :titulo";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("titulo", "Titulo de la Obra Testeada");

        Obra ObraObtenida = (Obra) query.getSingleResult();

        assertThat(ObraObtenida, is(equalTo(Obra)));
    }

}
