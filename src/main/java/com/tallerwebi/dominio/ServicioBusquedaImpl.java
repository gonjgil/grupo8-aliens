package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronResultadosException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ServicioBusquedaImpl implements ServicioBusqueda {

    private RepositorioObra repositorioObra;
    private RepositorioArtista repositorioArtista;

    public ServicioBusquedaImpl(RepositorioObra repositorioObra, RepositorioArtista repositorioArtista) {
        this.repositorioObra = repositorioObra;
        this.repositorioArtista = repositorioArtista;
    }

    @Override
    public List<Obra> buscarObraPorTitulo(String titulo) throws NoSeEncontraronResultadosException {
         List<Obra> obras = repositorioObra.buscarPorTitulo(titulo);
         if(obras.isEmpty()) {
             throw new NoSeEncontraronResultadosException("No se encontraron obras con el título proporcionado.");
         }
         return obras;
    }

    @Override
    public List<Artista> buscarArtistaPorNombre(String nombre) throws NoSeEncontraronResultadosException {
        List<Artista> artistas = repositorioArtista.obtenerPorNombre(nombre);
        if(artistas.isEmpty()) {
            throw new NoSeEncontraronResultadosException("No se encontraron artistas con el nombre proporcionado.");
        }
        return artistas;
    }

    @Override
    public List<Obra> buscarObraPorCategoria(Categoria categoria) throws NoSeEncontraronResultadosException {
        List<Obra> obras = repositorioObra.obtenerPorCategoria(categoria);
        if (obras.isEmpty()) {
            throw new NoSeEncontraronResultadosException("No se encontraron obras en la categoría proporcionada.");
        } else {
            return obras;
        }
    }

    @Override
    public List<Obra> buscarPorRangoDePrecios(Double precioMin, Double precioMax) throws NoSeEncontraronResultadosException {
        List<Obra> obras = repositorioObra.obtenerPorRangoDePrecio(precioMin, precioMax);
        if (obras.isEmpty()) {
            throw new NoSeEncontraronResultadosException("No se encontraron obras en el rango de precios proporcionado.");
        } else {
            return obras;
        }
    }

    @Override
    public List<Obra> buscarObraPorAutor(String nombreAutor) throws NoSeEncontraronResultadosException {
        List<Obra> obras = repositorioObra.obtenerPorAutor(nombreAutor);
        if (obras.isEmpty()) {
            throw new NoSeEncontraronResultadosException("No se encontraron obras del autor proporcionado.");
        } else {
            return obras;
        }
    }

    private List<Obra> ordenarPorPrecio(boolean asc) {
        List<Obra> obras = new ArrayList<>(repositorioObra.obtenerTodas());
        obras.sort(Comparator.comparingDouble(Obra::getPrecio));
        if (!asc) Collections.reverse(obras);
        return obras;
    }

    @Override
    public List<Obra> buscarPorPrecioAscendente() {
        return ordenarPorPrecio(true);
    }

    @Override
    public List<Obra> buscarPorPrecioDescendente() {
        return ordenarPorPrecio(false);
    }

    private List<Obra> ordenarPorCantidadDeLikes(boolean desc) {
        List<Obra> obras = new ArrayList<>(repositorioObra.obtenerTodas());
        obras.sort(Comparator.comparingInt(o -> o.getUsuariosQueDieronLike().size()));
        if (desc) Collections.reverse(obras);
        return obras;
    }

    @Override
    public List<Obra> buscarPorMayorCantidadDeLikes() {
        return ordenarPorCantidadDeLikes(true);
    }

    @Override
    public List<Obra> buscarPorMenorCantidadDeLikes() {
        return ordenarPorCantidadDeLikes(false);
    }

    @Override
    public List<Obra> buscarObraPorDescripcion(String descripcion) throws NoSeEncontraronResultadosException {
        List<Obra> obras = repositorioObra.buscarPorDescripcion(descripcion);
        if (obras.isEmpty()) {
            throw new NoSeEncontraronResultadosException("No se encontraron obras con la descripción proporcionada.");
        } else {
            return obras;
        }
    }
}
