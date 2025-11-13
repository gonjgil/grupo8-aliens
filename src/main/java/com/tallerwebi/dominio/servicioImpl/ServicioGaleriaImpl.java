package com.tallerwebi.dominio.servicioImpl;

import java.util.*;
import java.util.stream.Collectors;

import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dto.ObraDto;
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
        List<Obra> todas = repositorioObra.obtenerTodas();
        Collections.shuffle(todas);
        return convertirYValidar(todas);
    }

    @Override
    public List<Obra> obtenerPorAutor(String autor) {
        return convertirYValidar((repositorioObra.obtenerPorAutor(autor)));
    }

    @Override
    public List<Obra> obtenerPorCategoria(Categoria categoria) {
        return convertirYValidar(repositorioObra.obtenerPorCategoria(categoria));
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

    @Override
    public void actualizarObra(Long idObra, ObraDto dto, List<String> categoriasSeleccionadas, String urlImagen) throws NoExisteLaObra {
        Obra obraExistente = obtenerPorId(idObra);
        if (obraExistente == null) {
            throw new NoExisteArtista();
        }

        if (urlImagen == null || urlImagen.isEmpty()) {
            urlImagen = obraExistente.getImagenUrl();
        }

        obraExistente.setTitulo(dto.getTitulo());
        obraExistente.setDescripcion(dto.getDescripcion());
        obraExistente.setImagenUrl(urlImagen);

        if (categoriasSeleccionadas != null && !categoriasSeleccionadas.isEmpty()) {
            Set<Categoria> nuevasCategorias = categoriasSeleccionadas.stream()
                    .map(Categoria::valueOf)
                    .collect(Collectors.toSet());
            obraExistente.setCategorias(nuevasCategorias);
        }

        guardar(obraExistente, obraExistente.getArtista(), urlImagen);
    }
}