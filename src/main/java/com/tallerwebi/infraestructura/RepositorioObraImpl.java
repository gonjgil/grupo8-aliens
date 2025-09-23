package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.RepositorioObra;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class RepositorioObraImpl implements RepositorioObra {

    @Override
    public List<Obra> obtenerTodas() {
        return Collections.emptyList();
    }

    @Override
    public List<Obra> obtenerPorAutor(String autor) {
        return Collections.emptyList();
    }

     @Override
     public Obra obtenerPorId(Long id) {
        return new Obra();
     }
}