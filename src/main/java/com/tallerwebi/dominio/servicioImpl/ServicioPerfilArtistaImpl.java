package com.tallerwebi.dominio.servicioImpl;

import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.repositorios.RepositorioArtista;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.presentacion.dto.ObraDto;
import com.tallerwebi.presentacion.dto.PerfilArtistaDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("servicioPerfilArtista")
@Transactional
public class ServicioPerfilArtistaImpl implements ServicioPerfilArtista {

    private RepositorioArtista repositorioArtista;
    private RepositorioObra repositorioObra;

    public ServicioPerfilArtistaImpl(RepositorioArtista repositorioArtista, RepositorioObra repositorioObra) {
        this.repositorioArtista = repositorioArtista;
        this.repositorioObra = repositorioObra;
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

        //verifica si ya tiene un artista
        Artista existente = repositorioArtista.buscarArtistaPorUsuario(usuario);
        if(existente !=null){
            return existente;
        }

        // Lógica de corrección de urls antes de crear la entidad
        datos.setUrlFacebook(corregirUrl(datos.getUrlFacebook()));
        datos.setUrlInstagram(corregirUrl(datos.getUrlInstagram()));
        datos.setUrlTwitter(corregirUrl(datos.getUrlTwitter()));
        datos.setUrlFotoPerfil(datos.getUrlFotoPerfil());


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

    @Override
    public Artista obtenerArtistaPorUsuario(Usuario usuario) {
        return repositorioArtista.buscarArtistaPorUsuario(usuario);
    }

    @Override
    public List<ObraDto> obtenerObrasPorArtista(Long idArtista) {
        Artista artista = repositorioArtista.buscarArtistaPorId(idArtista);
        List<Obra> obtenerObras =repositorioObra.obtenerPorArtista(artista);
        List<ObraDto> obrasDto =new ArrayList<>();
        for (Obra obra : obtenerObras) {
            obrasDto.add(new ObraDto(obra));
        }
        return obrasDto;
    }
}

