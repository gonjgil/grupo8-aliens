package com.tallerwebi.infraestructura;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.RepositorioObra;
import com.tallerwebi.dominio.enums.Categoria;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioObraImpl implements RepositorioObra {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioObraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Obra obra) {
        this.sessionFactory.getCurrentSession().save(obra);
    }

    @Override
    public List<Obra> obtenerTodas() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM Obra", Obra.class)
                .getResultList();
    }

    @Override
    public List<Obra> obtenerPorAutor(String autor) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM Obra WHERE autor = :autor", Obra.class)
                .setParameter("autor", autor)
                .getResultList();
    }

    @Override
    public List<Obra> obtenerPorCategoria(String categoria) {
        try {
            return this.sessionFactory.getCurrentSession()
                    .createQuery("SELECT DISTINCT o FROM Obra o JOIN o.categorias c WHERE c = :categoria", Obra.class)
                    .setParameter("categoria", Categoria.valueOf(categoria.toUpperCase()))
                    .getResultList();
        } catch (IllegalArgumentException e) {
            // Si la categoría no existe en el enum, devolver lista vacía
            return new ArrayList<>();
        }
    }

    @Override
    public Obra obtenerPorId(Long id) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM Obra o LEFT JOIN FETCH o.usuariosQueDieronLike WHERE o.id = :id", Obra.class)
                .setParameter("id", id)
                .uniqueResult();
    }
}