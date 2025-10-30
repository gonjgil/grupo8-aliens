package com.tallerwebi.dominio;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayObrasExistentes;

@Service("servicioGaleria")
@Transactional
public class ServicioGaleriaImpl implements ServicioGaleria {

    private final RepositorioObra repositorioObra;

    @Autowired
    public ServicioGaleriaImpl(RepositorioObra repositorioObra) {
        this.repositorioObra = repositorioObra;
    }

    private List<Obra> convertirYValidar (List<Obra> obras) throws NoHayObrasExistentes {
        if (obras == null || obras.isEmpty()) {
            throw new NoHayObrasExistentes();
        }
        return obras;
    }

    @Override
    public List<Obra> obtener() throws NoHayObrasExistentes {
        return convertirYValidar(repositorioObra.obtenerTodas());
    }

    @Override
    public List<Obra> ordenarRandom() {
        try {
            List<Obra> todas = repositorioObra.obtenerTodas();
            Collections.shuffle(todas);
            return convertirYValidar(todas);
        } catch (NoHayObrasExistentes e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Obra> obtenerPorAutor(String autor) {
        try {
            return convertirYValidar((repositorioObra.obtenerPorAutor(autor)));
        } catch (NoHayObrasExistentes e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Obra> obtenerPorCategoria(Categoria categoria) {
        try {
            return convertirYValidar(repositorioObra.obtenerPorCategoria(categoria));
        } catch (NoHayObrasExistentes e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Obra obtenerPorId(Long id) throws NoExisteLaObra {
        Obra obra = repositorioObra.obtenerPorId(id);
        if (obra == null) {
            throw new NoExisteLaObra();
        }
        return obra;
    }

    @Override
    public List<Obra> obtenerObrasParaUsuario(Usuario usuario) {
        try {
            List<Obra> todasLasObras = convertirYValidar(repositorioObra.obtenerTodas());
            Set<Obra> obrasLikeadas = usuario.getObrasLikeadas();

            if (obrasLikeadas == null || obrasLikeadas.isEmpty()) {
                List<Obra> aleatorias = new ArrayList<>(todasLasObras);
                Collections.shuffle(aleatorias);
                return aleatorias;
            }

            // Artistas y categorías de interés
            Set<Artista> artistasDeInteres = obrasLikeadas.stream()
                    .map(Obra::getArtista)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            Set<Categoria> categoriasDeInteres = obrasLikeadas.stream()
                    .flatMap(o -> o.getCategorias().stream())
                    .collect(Collectors.toSet());

            // Filtrar obras de interés usando Set para evitar duplicados
            Set<Obra> setFiltradas = todasLasObras.stream()
                    .filter(o ->
                            obrasLikeadas.contains(o) ||
                                    artistasDeInteres.contains(o.getArtista()) ||
                                    o.getCategorias().stream().anyMatch(categoriasDeInteres::contains)
                    )
                    .collect(Collectors.toCollection(LinkedHashSet::new)); // mantiene orden de inserción

            // Orden aleatorio penalizando las obras likeadas
            List<Obra> listaFiltrada = new ArrayList<>(setFiltradas);
            listaFiltrada.sort(Comparator.comparingDouble(o -> {
                double base = Math.random();
                if (obrasLikeadas.contains(o)) base += 0.4; // penaliza un poco más
                return base;
            }));

            // Obras no relacionadas
            List<Obra> obrasNoRelacionadas = todasLasObras.stream()
                    .filter(o -> !setFiltradas.contains(o))
                    .collect(Collectors.toList());
            Collections.shuffle(obrasNoRelacionadas);

            listaFiltrada.addAll(obrasNoRelacionadas);

            LinkedHashMap<Long, Obra> mapaFinal = new LinkedHashMap<>();
            for (Obra obra : listaFiltrada) {
                mapaFinal.putIfAbsent(obra.getId(), obra);
            }

            return new ArrayList<>(mapaFinal.values());
        } catch (NoHayObrasExistentes e) {
            return new ArrayList<>();
        }
    }


}