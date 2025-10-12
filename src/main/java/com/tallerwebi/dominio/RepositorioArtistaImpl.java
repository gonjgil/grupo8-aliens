package com.tallerwebi.dominio;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public Artista guardar(Artista artista) {
        sessionFactory.getCurrentSession().save(artista);
        return artista; // Devuelve el artista, que ya tendrá su ID asignado por save()
    }

    @Override
    public void modificar(Artista artista) {
        sessionFactory.getCurrentSession().update(artista);
    }
}
