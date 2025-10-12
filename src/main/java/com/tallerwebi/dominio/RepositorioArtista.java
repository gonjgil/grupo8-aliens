package com.tallerwebi.dominio;

public interface RepositorioArtista {
    Artista buscarArtistaPorId(Long id);
// agregar metodo para buscar por usuario si cada usuario es un artista
    //Artista buscarArtistaPorUsuario(Usuario usuario);

    Artista guardar(Artista artista);

    void modificar(Artista artista);


}
