package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tallerwebi.dominio.excepcion.UsuarioAnonimoException;
import com.tallerwebi.presentacion.ObraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayObrasExistentes;

@Service("servicioGaleria")
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
        return convertirYValidar(repositorioObra.obtenerPorAutor(autor));
    }

    @Override
    public List<Obra> obtenerPorCategoria(String categoria) {
        // return convertirYValidar(repositorioObra.obtenerPorCategoria(categoria));
        return new ArrayList<>(); // No implementado
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
    public void darLike(Long id, Usuario usuario) throws NoExisteLaObra, UsuarioAnonimoException {
        if (usuario == null) {
            throw new UsuarioAnonimoException();
        }
        Obra obra = repositorioObra.obtenerPorId(id);
        if (obra == null) {
            throw new NoExisteLaObra();
        }
        repositorioObra.darLike(obra, usuario);
    }
}