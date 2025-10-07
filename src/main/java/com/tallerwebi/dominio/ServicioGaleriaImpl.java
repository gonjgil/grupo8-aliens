package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tallerwebi.dominio.excepcion.UsuarioAnonimoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerwebi.dominio.enums.Categoria;
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
    @Transactional(readOnly = true)
    public List<Obra> obtener() throws NoHayObrasExistentes {
        return convertirYValidar(repositorioObra.obtenerTodas());
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<Obra> obtenerPorAutor(String autor) {
        try {
            return convertirYValidar((repositorioObra.obtenerPorAutor(autor)));
        } catch (NoHayObrasExistentes e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Obra> obtenerPorCategoria(Categoria categoria) {
        try {
            return convertirYValidar(repositorioObra.obtenerPorCategoria(categoria));
        } catch (NoHayObrasExistentes e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public Obra obtenerPorId(Long id) throws NoExisteLaObra {
        Obra obra = repositorioObra.obtenerPorId(id);
        if (obra == null) {
            throw new NoExisteLaObra();
        }
        return obra;
    }

    @Override
    @Transactional
    public void darLike(Long obraId, Usuario usuario) throws NoExisteLaObra {
        repositorioObra.darLike(obraId, usuario);
    }

    @Override
    @Transactional
    public void quitarLike(Long obraId, Usuario usuario) {
        repositorioObra.quitarLike(obraId, usuario);
    }

    @Override
    @Transactional
    public void toggleLike(Long obraId, Usuario usuario) throws NoExisteLaObra, UsuarioAnonimoException {
        if (usuario == null) {
            throw new UsuarioAnonimoException();
        }
        Obra obra = repositorioObra.obtenerPorId(obraId);
        if (obra == null) {
            throw new NoExisteLaObra();
        }

        if (obra.getUsuariosQueDieronLike().contains(usuario)) {
            this.quitarLike(obraId, usuario);
        } else {
            this.darLike(obraId, usuario);
        }
    }
}