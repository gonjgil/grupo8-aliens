package com.tallerwebi.dominio;


import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.presentacion.dto.ObraDto;
import com.tallerwebi.presentacion.dto.PerfilArtistaDTO;

import java.util.List;

public interface ServicioPerfilArtista {
    PerfilArtistaDTO obtenerPerfilArtista (Long idArtista);
    Artista crearPerfilArtista (PerfilArtistaDTO datos, Usuario usuario);// necesario al registrar un nuevo artista
    void actualizarPerfilArtista (PerfilArtistaDTO datos) throws NoExisteArtista;
    Artista obtenerArtistaPorUsuario(Usuario usuario);
    List<ObraDto> obtenerObrasPorArtista(Long idArtista);
}

