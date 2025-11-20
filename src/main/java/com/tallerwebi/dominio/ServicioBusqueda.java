package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronResultadosException;

import java.util.List;

public interface ServicioBusqueda {
    List<Artista> buscarArtistaPorNombre(String nombre) throws NoSeEncontraronResultadosException;
    List<Obra> buscarPorRangoDePrecios(Double precioMin, Double precioMax) throws NoSeEncontraronResultadosException;
    List<Obra> buscarPorMayorCantidadDeLikes();
    List<Obra> buscarPorMenorCantidadDeLikes();
    List<Obra> buscarObrasPorString(String palabraBuscada);
}
