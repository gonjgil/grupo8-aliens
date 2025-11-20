package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Comentario;
import com.tallerwebi.dominio.repositorios.RepositorioComentario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioComentarioImpl implements RepositorioComentario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioComentarioImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    public RepositorioComentarioImpl() {}

    @Override
    public void guardar(Comentario comentario) {
        sessionFactory.getCurrentSession().save(comentario);

    }

    @Override
    public List<Comentario> obtenerPorObra(Long obraId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Comentario c JOIN FETCH c.usuario WHERE c.obra.id = :obraId ORDER BY c.fecha ASC", Comentario.class)
                .setParameter("obraId", obraId)
                .getResultList();
    }
}
