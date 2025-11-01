package com.tallerwebi.dominio;


import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.presentacion.dto.PerfilArtistaDTO;

public interface ServicioPerfilArtista {
    PerfilArtistaDTO obtenerPerfilArtista (Long idArtista);
    Artista crearPerfilArtista (PerfilArtistaDTO datos, Usuario usuario);// necesario al registrar un nuevo artista
    void actualizarPerfilArtista (PerfilArtistaDTO datos) throws NoExisteArtista;
}

