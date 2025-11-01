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
    public Boolean crearOrdenCompra(OrdenCompra ordenCompra) {
        return (Long) sessionFactory.getCurrentSession().save(ordenCompra) > 0;
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
}
