package com.tallerwebi.infraestructura;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.RepositorioObra;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.excepcion.NoHayObrasExistentes;

@Service
public class ServicioGaleriaImpl implements ServicioGaleria {

    private final RepositorioObra repositorioObra;

    @Autowired
    public ServicioGaleriaImpl (RepositorioObra repositorioObra) {
        this.repositorioObra = repositorioObra;
    }

    @Override
    public List<ObraDto> obtener() throws NoHayObrasExistentes {
        List<Obra> obras = repositorioObra.obtenerTodas();
        if (obras == null || obras.isEmpty()) {
            throw new NoHayObrasExistentes();
        }
        List<ObraDto> dtos = new ArrayList<>();
        for (Obra obra : obras) {
            dtos.add(new ObraDto(obra));
        }
        return dtos;
    }
}