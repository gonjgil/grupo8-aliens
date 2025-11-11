package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.entidades.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email) {

        final Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT u FROM Usuario u " +
                "LEFT JOIN FETCH u.obrasLikeadas " +
                "WHERE u.email = :email";

        return session.createQuery(hql, Usuario.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().saveOrUpdate(usuario);
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

}
