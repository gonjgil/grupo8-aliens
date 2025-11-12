package com.tallerwebi.dominio.servicioImpl;

import java.util.*;
import java.util.stream.Collectors;

import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.NoExisteFormatoObra;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
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
            Set<Categoria> categoriasFavoritas = usuario.getCategoriasFavoritas(); // 👈 NUEVO

            if ((obrasLikeadas == null || obrasLikeadas.isEmpty()) &&
                    (categoriasFavoritas == null || categoriasFavoritas.isEmpty())) {
                List<Obra> aleatorias = new ArrayList<>(todasLasObras);
                Collections.shuffle(aleatorias);
                return aleatorias;
            }

            // Artistas y categorías de interés derivadas de likes
            Set<Artista> artistasDeInteres = obrasLikeadas == null ? new HashSet<>() :
                    obrasLikeadas.stream()
                            .map(Obra::getArtista)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

            Set<Categoria> categoriasDeInteres = obrasLikeadas == null ? new HashSet<>() :
                    obrasLikeadas.stream()
                            .flatMap(o -> o.getCategorias().stream())
                            .collect(Collectors.toSet());

            // Combina categorías de interés con favoritas
            if (categoriasFavoritas != null) {
                categoriasDeInteres.addAll(categoriasFavoritas);
            }

            // Filtra obras relevantes (por artista o categoría)
            Set<Obra> setFiltradas = todasLasObras.stream()
                    .filter(o ->
                            (obrasLikeadas != null && obrasLikeadas.contains(o)) ||
                                    artistasDeInteres.contains(o.getArtista()) ||
                                    o.getCategorias().stream().anyMatch(categoriasDeInteres::contains)
                    )
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            // Ordena con prioridad: favoritas -> likeadas -> resto
            List<Obra> listaFiltrada = new ArrayList<>(setFiltradas);
            listaFiltrada.sort(Comparator.comparingDouble(o -> {
                double prioridad = Math.random();
                if (categoriasFavoritas != null && o.getCategorias().stream().anyMatch(categoriasFavoritas::contains))
                    prioridad -= 0.5; // prioriza favoritas
                if (obrasLikeadas != null && obrasLikeadas.contains(o))
                    prioridad += 0.4; // penaliza likeadas para no repetir
                return prioridad;
            }));

            // Agrega las no relacionadas al final
            List<Obra> obrasNoRelacionadas = todasLasObras.stream()
                    .filter(o -> !setFiltradas.contains(o))
                    .collect(Collectors.toList());
            Collections.shuffle(obrasNoRelacionadas);
            listaFiltrada.addAll(obrasNoRelacionadas);

            // Elimina duplicados preservando orden
            LinkedHashMap<Long, Obra> mapaFinal = new LinkedHashMap<>();
            for (Obra obra : listaFiltrada) {
                mapaFinal.putIfAbsent(obra.getId(), obra);
            }

            return new ArrayList<>(mapaFinal.values());

        } catch (NoHayObrasExistentes e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Obra guardar(Obra obra, Artista artista, String urlImagen) {
        if(obra == null) {
            throw new NullPointerException("La obra no puede ser nula");
        }
        if(urlImagen != null && !urlImagen.isEmpty()) {
            obra.setImagenUrl(urlImagen);
        }
        obra.setArtista(artista);
        return repositorioObra.guardar(obra);
    }

}