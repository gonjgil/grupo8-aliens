package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tallerwebi.dominio.excepcion.UsuarioAnonimoException;
import com.tallerwebi.presentacion.ObraDto;

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
    public ObraDto obtenerPorId(Long id) throws NoExisteLaObra {
        Obra obra = repositorioObra.obtenerPorId(id);
        if (obra == null) {
            throw new NoExisteLaObra();
        }
        return new ObraDto(obra);
    }

}