package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronResultadosException;

import java.util.List;

public interface ServicioBusqueda {
    List<Obra> buscarObraPorTitulo(String titulo) throws NoSeEncontraronResultadosException;
    List<Artista> buscarArtistaPorNombre(String nombre) throws NoSeEncontraronResultadosException;
    List<Obra> buscarObraPorCategoria(Categoria categoria) throws NoSeEncontraronResultadosException;
    List<Obra> buscarPorRangoDePrecios(Double precioMin, Double precioMax) throws NoSeEncontraronResultadosException;
    List<Obra> buscarObraPorAutor(String nombreAutor) throws NoSeEncontraronResultadosException;
    List<Obra> buscarPorPrecioAscendente();
    List<Obra> buscarPorPrecioDescendente();

    List<Obra> buscarPorMayorCantidadDeLikes();

    List<Obra> buscarPorMenorCantidadDeLikes();

    List<Obra> buscarObraPorDescripcion(String descripcion) throws NoSeEncontraronResultadosException;
}
