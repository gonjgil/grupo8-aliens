package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private List<ObraDto> convertirYValidar (List<Obra> obras) throws NoHayObrasExistentes {
        if (obras == null || obras.isEmpty()) {
            throw new NoHayObrasExistentes();
        }
        List<ObraDto> dtos = new ArrayList<>();
        for (Obra obra : obras) {
            dtos.add(new ObraDto(obra));
        }
        return dtos;
    }

    @Override
    public List<ObraDto> obtener() throws NoHayObrasExistentes {
        return convertirYValidar(repositorioObra.obtenerTodas());
    }

    @Override
    public List<ObraDto> ordenarRandom() {
        List<Obra> todas = repositorioObra.obtenerTodas();
        Collections.shuffle(todas);
        return convertirYValidar(todas);
    }

    @Override
    public List<ObraDto> obtenerPorAutor(String autor) {
        return convertirYValidar((repositorioObra.obtenerPorAutor(autor)));
    }

    @Override
    public List<ObraDto> obtenerPorCategoria(String categoria) {
        // return convertirYValidar(repositorioObra.obtenerPorCategoria(categoria));
        return new ArrayList<>(); // No implementado
    }

    @Override
    public ObraDto obtenerPorId(Long id) throws NoExisteLaObra {
        Obra obra = repositorioObra.obtenerPorId(id);
        if (obra == null) {
            throw new NoExisteLaObra();
        }
        ObraDto obraDto = new ObraDto(obra);
        return obraDto;
    }

    @Override
    public void darLike(Long id, Usuario usuario) throws NoExisteLaObra {
        Obra obra = repositorioObra.obtenerPorId(id);
        if (obra == null) {
            throw new NoExisteLaObra();
        }
        obra.darLike(usuario);
    }
}