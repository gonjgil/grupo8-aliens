package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.OrdenCompra;
import com.tallerwebi.dominio.repositorios.RepositorioOrdenCompra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.EstadoOrdenCompra;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository("RepositorioOrdenCompra")
public class RepositorioOrdenCompraImpl implements RepositorioOrdenCompra {


    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioOrdenCompraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardar(OrdenCompra ordenCompra) {
        sessionFactory.getCurrentSession().save(ordenCompra);
    }

    @Override
    public void eliminar(OrdenCompra ordenCompra) {
        sessionFactory.getCurrentSession().delete(ordenCompra);
    }

    @Override
    public OrdenCompra obtenerPorId(Long id) {
        return sessionFactory.getCurrentSession().get(OrdenCompra.class, id);
    }

    @Override
    public List<OrdenCompra> obtenerPorEstado(EstadoOrdenCompra estado) {
        String hql = "FROM OrdenCompra o WHERE o.estado = :estado";
        return sessionFactory.getCurrentSession()
                .createQuery(hql,OrdenCompra.class)
                .setParameter("estado", estado)
                .getResultList();
    }


    @Override
    public List<OrdenCompra> obtenerTodasPorUsuario(Usuario usuario) {
        String hql = "FROM OrdenCompra o WHERE o.usuario = :usuario";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, OrdenCompra.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }

    @Override
    public void actualizarEstado(Long ordenId, EstadoOrdenCompra estado) {
        this.sessionFactory.getCurrentSession()
                .createQuery("UPDATE OrdenCompra o SET o.estado = :estado WHERE o.id = :id")
                .setParameter("estado", estado)
                .setParameter("id", ordenId)
                .executeUpdate();
        OrdenCompra ordenActualizada = sessionFactory.getCurrentSession().get(OrdenCompra.class, ordenId);
        sessionFactory.getCurrentSession().refresh(ordenActualizada);

    }

    @Override
    public List<OrdenCompra> obtenerTodas() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM OrdenCompra",OrdenCompra.class)
                .getResultList();
    }
}
