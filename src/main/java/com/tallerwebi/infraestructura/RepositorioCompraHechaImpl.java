package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.enums.EstadoPago;
import com.tallerwebi.dominio.repositorios.RepositorioCompraHecha;
import com.tallerwebi.dominio.entidades.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository("RepositorioCompraHecha")
public class RepositorioCompraHechaImpl implements RepositorioCompraHecha {


    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioCompraHechaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public CompraHecha guardar(CompraHecha compraHecha) {
        Session session = sessionFactory.getCurrentSession();
        session.save(compraHecha);

        return compraHecha;
    }

    @Override
    public void eliminar(CompraHecha compraHecha) {
        sessionFactory.getCurrentSession().delete(compraHecha);
    }

    @Override
    public CompraHecha obtenerPorId(Long id) {
        return sessionFactory.getCurrentSession().get(CompraHecha.class, id);
    }


    @Override
    public List<CompraHecha> obtenerTodasPorUsuario(Usuario usuario) {
        String hql =  "SELECT DISTINCT o FROM CompraHecha o LEFT JOIN FETCH o.items WHERE o.usuario = :usuario";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, CompraHecha.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }

    @Override
    public List<CompraHecha> obtenerTodas() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM CompraHecha", CompraHecha.class)
                .getResultList();
    }

    @Override
    public void actualizarEstado(Long compraId, EstadoPago estado) {
        this.sessionFactory.getCurrentSession()
                .createQuery("UPDATE CompraHecha o SET o.estadoPago = :estado WHERE o.id = :id")
                .setParameter("estado", estado)
                .setParameter("id", compraId)
                .executeUpdate();
        CompraHecha ordenActualizada = sessionFactory.getCurrentSession().get(CompraHecha.class, compraId);
        sessionFactory.getCurrentSession().refresh(ordenActualizada);
    }
}
