package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioArtista;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioArtista")
public class RepositorioArtistaImpl implements RepositorioArtista {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioArtistaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Artista buscarArtistaPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Artista.class, id);
    }

    @Override
    public Artista buscarArtistaPorUsuario(Usuario usuario) {
        String hql = "FROM Artista a WHERE a.usuario = :usuario";
        return (Artista) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("usuario", usuario)
                .uniqueResult();
    }


    @Override
    public Artista guardar(Artista artista) {
        sessionFactory.getCurrentSession().save(artista);
        return artista; // Devuelve el artista, que ya tendrá su ID asignado por save()
    }

    @Override
    public void modificar(Artista artista) {
        sessionFactory.getCurrentSession().update(artista);
    }

    @Override
    public List<Artista> obtenerPorNombre(String nombre) {
        String hql = "FROM Artista WHERE lower(nombre) LIKE :nombre";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Artista.class)
                .setParameter("nombre", "%" + nombre.toLowerCase() + "%")
                .getResultList();
    }

    @Override
    public void eliminar(Artista artista) {
        sessionFactory.getCurrentSession().delete(artista);
    }

}
