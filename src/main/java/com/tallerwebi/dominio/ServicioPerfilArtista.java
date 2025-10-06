package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.presentacion.PerfilArtistaDTO;

public interface ServicioPerfilArtista {
    PerfilArtistaDTO obtenerPerfilArtista (Long idArtista);
    Artista crearPerfilArtista (PerfilArtistaDTO datos);// necesario al registrar un nuevo artista
    void actualizarPerfilArtista (PerfilArtistaDTO datos) throws NoExisteArtista;
}

