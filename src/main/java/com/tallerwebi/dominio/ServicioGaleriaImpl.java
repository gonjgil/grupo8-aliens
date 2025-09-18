package com.tallerwebi.dominio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tallerwebi.presentacion.ObraDto;

@Service
public class ServicioGaleriaImpl implements ServicioGaleria{
    
    @Override
    public List<ObraDto> obtener() {
        throw new UnsupportedOperationException("Unimplemented method 'obtener'");
    }
    
}
