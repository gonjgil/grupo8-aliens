package com.tallerwebi.dominio.servicioImpl;

import com.tallerwebi.dominio.repositorios.RepositorioArtista;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.ServicioBusqueda;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronResultadosException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ServicioBusquedaImpl implements ServicioBusqueda {

    private RepositorioObra repositorioObra;
    private RepositorioArtista repositorioArtista;

    public ServicioBusquedaImpl(RepositorioObra repositorioObra, RepositorioArtista repositorioArtista) {
        this.repositorioObra = repositorioObra;
        this.repositorioArtista = repositorioArtista;
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
    public List<Obra> buscarPorRangoDePrecios(Double precioMin, Double precioMax) throws NoSeEncontraronResultadosException {
        List<Obra> obras = repositorioObra.obtenerPorRangoDePrecio(precioMin, precioMax);
        if (obras.isEmpty()) {
            throw new NoSeEncontraronResultadosException("No se encontraron obras en el rango de precios proporcionado.");
        }
        return obras;
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
    public List<Obra> buscarObrasPorString(String palabraBuscada) {
        List<Obra> obras = repositorioObra.buscarPorString(palabraBuscada);
        if (obras.isEmpty()) {
            throw new NoSeEncontraronResultadosException("No se encontraron obras");
        } else {
            return obras;
        }
    }
}
