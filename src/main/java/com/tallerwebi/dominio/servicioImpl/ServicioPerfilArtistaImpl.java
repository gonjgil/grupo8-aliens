package com.tallerwebi.dominio.servicioImpl;

import com.tallerwebi.dominio.repositorios.RepositorioArtista;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.presentacion.dto.PerfilArtistaDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioPerfilArtista")
@Transactional
public class ServicioPerfilArtistaImpl implements ServicioPerfilArtista {

    private RepositorioArtista repositorioArtista;

    public ServicioPerfilArtistaImpl(RepositorioArtista repositorioArtista) {
        this.repositorioArtista = repositorioArtista;
    }

    @Override
    public PerfilArtistaDTO obtenerPerfilArtista(Long idArtista) throws NoExisteArtista{
        Artista artista = repositorioArtista.buscarArtistaPorId(idArtista);
        if (artista == null) {
            // Manejar caso donde el artista no existe (lanzar excepción)
            throw new NoExisteArtista();
        }

        //conversión de Entidad a dto
        PerfilArtistaDTO dto = new PerfilArtistaDTO();
        dto.setId(artista.getId()); // Asumiendo que PerfilArtistaDTO tiene un setter para id
        dto.setNombre(artista.getNombre());
        dto.setBiografia(artista.getBiografia());
        dto.setUrlFotoPerfil(artista.getUrlFotoPerfil());
        dto.setUrlFacebook(artista.getUrlFacebook());
        dto.setUrlInstagram(artista.getUrlInstagram());
        dto.setUrlTwitter(artista.getUrlTwitter());

        dto.setUsuarioId(artista.getUsuario() != null ? artista.getUsuario().getId() : null);

        // Servicio devuelve el dto al Controlador.
        return dto;
    }


    @Override
    public void actualizarPerfilArtista(PerfilArtistaDTO datos) throws NoExisteArtista {
        Artista artistaExistente = repositorioArtista.buscarArtistaPorId(datos.getId());
        if (artistaExistente == null) {
            // Manejar caso donde el artista no existe (lanzar excepción)
            throw new NoExisteArtista();
        } else {
            datos.setUrlFacebook(corregirUrl(datos.getUrlFacebook()));
            datos.setUrlInstagram(corregirUrl(datos.getUrlInstagram()));
            datos.setUrlTwitter(corregirUrl(datos.getUrlTwitter()));
            datos.setUrlFotoPerfil(corregirUrl(datos.getUrlFotoPerfil()));

            artistaExistente.setNombre(datos.getNombre());
            artistaExistente.setBiografia(datos.getBiografia());
            artistaExistente.setUrlFotoPerfil(datos.getUrlFotoPerfil());
            artistaExistente.setUrlFacebook(datos.getUrlFacebook());
            artistaExistente.setUrlInstagram(datos.getUrlInstagram());
            artistaExistente.setUrlTwitter(datos.getUrlTwitter());
            repositorioArtista.modificar(artistaExistente);
        }
    }

    @Override
    public Artista crearPerfilArtista(PerfilArtistaDTO datos, Usuario usuario) {

        // Lógica de corrección de urls antes de crear la entidad
        datos.setUrlFacebook(corregirUrl(datos.getUrlFacebook()));
        datos.setUrlInstagram(corregirUrl(datos.getUrlInstagram()));
        datos.setUrlTwitter(corregirUrl(datos.getUrlTwitter()));
        datos.setUrlFotoPerfil(datos.getUrlFotoPerfil());


// Hay que cambiar esto si ligamos a la creación de un Usuario a la asignación del rol de artista
        Artista nuevoArtista = new Artista(datos.getNombre(), datos.getBiografia(), datos.getUrlFotoPerfil());
        nuevoArtista.setUrlFacebook(corregirUrl(datos.getUrlFacebook()));
        nuevoArtista.setUrlInstagram(corregirUrl(datos.getUrlInstagram()));
        nuevoArtista.setUrlTwitter(corregirUrl(datos.getUrlTwitter()));
        nuevoArtista.setUsuario(usuario);

        repositorioArtista.guardar(nuevoArtista);

        return nuevoArtista;
    }

    private String corregirUrl(String url) {
        if (url != null && !url.isEmpty() && !url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }
}

