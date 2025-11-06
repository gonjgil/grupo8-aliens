package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.repositorios.RepositorioFormatoObra;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioFormatoObra")
public class RepositorioFormatoObraImpl implements RepositorioFormatoObra {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioFormatoObraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(FormatoObra formatoObra) {
        sessionFactory.getCurrentSession().saveOrUpdate(formatoObra);
    }

    @Override
    public FormatoObra obtenerPorId(Long id) {
        return sessionFactory.getCurrentSession().get(FormatoObra.class, id);
    }

    @Override
    public List<FormatoObra> obtenerFormatosPorObra(Long obraId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM FormatoObra f WHERE f.obra.id = :obraId", FormatoObra.class)
                .setParameter("obraId", obraId)
                .getResultList();
    }

    @Override
    public FormatoObra obtenerFormatoPorObraYFormato(Long obraId, Formato formato) {
        List<FormatoObra> resultados = sessionFactory.getCurrentSession()
                .createQuery("FROM FormatoObra f WHERE f.obra.id = :obraId AND f.formato = :formato", FormatoObra.class)
                .setParameter("obraId", obraId)
                .setParameter("formato", formato)
                .getResultList();
        
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public void eliminar(FormatoObra formatoObra) {
        sessionFactory.getCurrentSession().delete(formatoObra);
    }

    @Override
    public List<FormatoObra> obtenerTodos() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM FormatoObra", FormatoObra.class)
                .getResultList();
    }
}