package com.tallerwebi.dominio.servicioImpl;

import com.tallerwebi.dominio.ServicioEstadistica;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("servicioEstadistica")
@Transactional
public class ServicioEstadisticaImpl implements ServicioEstadistica {

    public RepositorioObra repositorioObra;

    public ServicioEstadisticaImpl(RepositorioObra repositorioObra) {
        this.repositorioObra = repositorioObra;
    }

    @Override
    public Map<Obra, Long> obtenerMasVendidasArtista(Artista artista) {
        if(artista == null){
            throw new NoExisteArtista();
        }
        return repositorioObra.obtenerMasVendidasPorArtista(artista);
    }

    @Override
    public Map<Obra, Long> obtenerMasLikeadasArtista(Artista artista) {
        if (artista == null){
            throw new NoExisteArtista();
        }
        List<Obra> obras = repositorioObra.obtenerMasLikeadasPorArtista(artista);
        HashMap<Obra, Long> map = new HashMap<>();

        for (Obra obra : obras) {
            map.put(obra, (long) obra.getCantidadLikes());
        }

        return map;
    }

    @Override
    public Map<Categoria, Long> obtenerTresCategoriasMasVendidasArtista(Artista artista) {
        if(artista == null){
            throw  new NoExisteArtista();
        }
        return repositorioObra.obtenerTresCategoriasMasVendidasArtista(artista);
    }

    @Override
    public Map<Categoria, Long> obtenerTresCategoriasMasLikeadasArtista(Artista artista) {
        if(artista == null){
            throw  new NoExisteArtista();
        }
        return repositorioObra.obtenerTresCategoriasMasLikeadasArtista(artista);
    }

    @Override
    public List<Obra> obtenerTrendingVentasArtista(Artista artista) {
        if(artista == null){
            throw  new NoExisteArtista();
        }
        return repositorioObra.obtenerTrendingVentasArtista(artista);
    }

    @Override
    public List<Obra> obtenerTrendingLikesArtista(Artista artista) {
        if(artista == null){
            throw  new NoExisteArtista();
        }
        return repositorioObra.obtenerTrendingLikesArtista(artista);
    }

}
