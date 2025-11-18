package com.tallerwebi.dominio.repositorios;

import java.util.List;
import java.util.Map;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.enums.Categoria;

public interface RepositorioObra {
    Obra guardar(Obra obra);
    List<Obra> obtenerTodas();
    List<Obra> obtenerPorArtista(Artista artista);
    List<Obra> obtenerPorCategoria(Categoria categoria);
    Obra obtenerPorId(Long id);
    //List<Obra> buscarPorTitulo(String titulo);
    List<Obra> obtenerPorRangoDePrecio(Double precioMin, Double precioMax);
    //List<Obra> buscarPorDescripcion(String descripcion);
    List<Obra> buscarPorString(String palabraBuscada);
    void eliminar(Obra obra);

    Map<Obra, Long> obtenerMasVendidasPorArtista(Artista artista);
    List<Obra> obtenerMasLikeadasPorArtista(Artista artista);
    Map<Categoria, Long> obtenerTresCategoriasMasVendidasArtista(Artista artista);
    Map<Categoria, Long> obtenerTresCategoriasMasLikeadasArtista(Artista artista);
    List<Obra> obtenerTrendingVentasArtista(Artista artista);
    List<Obra> obtenerTrendingLikesArtista(Artista artista);
}