package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Usuario;
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

    @Override // testeado
    public void guardar(Obra obra) {
        this.sessionFactory.getCurrentSession().save(obra);
    }

    @Override // testeado
    public List<Obra> obtenerTodas() {
        try {
            return this.sessionFactory.getCurrentSession()
                    .createQuery("FROM Obra", Obra.class)
                    .getResultList();
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    @Override // testeado
    public List<Obra> obtenerPorAutor(String autor) {
        try {
            return this.sessionFactory.getCurrentSession()
                    .createQuery("FROM Obra WHERE autor = :autor", Obra.class)
                    .setParameter("autor", autor)
                    .getResultList();
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    @Override // testeado
    public List<Obra> obtenerPorCategoria(Categoria categoria) {
        try {
            return this.sessionFactory.getCurrentSession()
                    .createQuery("SELECT DISTINCT o FROM Obra o JOIN o.categorias c WHERE c = :categoria", Obra.class)
                    .setParameter("categoria", Categoria.valueOf(categoria.name()))
                    .getResultList();
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    @Override // testeado
    public Obra obtenerPorId(Long id) {
        try {
            return this.sessionFactory.getCurrentSession()
                    .createQuery("FROM Obra o LEFT JOIN FETCH o.usuariosQueDieronLike WHERE o.id = :id", Obra.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public void darLike(Long id, Usuario usuario) {
        Obra obra = obtenerPorId(id);
        if (obra == null) {
            throw new IllegalArgumentException("No existe la obra con id: " + id);
        }
        obra.getUsuariosQueDieronLike().add(usuario);
        this.sessionFactory.getCurrentSession().merge(obra);
    }

    @Override
    public void quitarLike(Long id, Usuario usuario) {
        Obra obra = obtenerPorId(id);
        if (obra != null) {
            boolean removed = obra.getUsuariosQueDieronLike().remove(usuario);
            if (removed) {
                sessionFactory.getCurrentSession().merge(obra);
                System.out.println("✅ Like removido exitosamente.");
            } else {
                System.out.println("⚠️ No se encontró el usuario en la colección para quitar el like.");
            }
        }
    }
}