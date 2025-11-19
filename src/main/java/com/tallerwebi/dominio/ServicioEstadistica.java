package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.enums.Categoria;

import java.util.List;
import java.util.Map;

public interface ServicioEstadistica {
    Map<Obra, Long> obtenerMasVendidasArtista(Artista artista);
    Map<Obra, Long> obtenerMasLikeadasArtista(Artista artista);
    Map<Categoria, Long> obtenerTresCategoriasMasVendidasArtista(Artista artista);
    Map<Categoria, Long> obtenerTresCategoriasMasLikeadasArtista(Artista artista);
    List<Obra> obtenerTrendingVentasArtista(Artista artista);
    List<Obra> obtenerTrendingLikesArtista(Artista artista);
}
