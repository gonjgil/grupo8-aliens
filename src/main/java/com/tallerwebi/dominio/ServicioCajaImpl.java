package com.tallerwebi.dominio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tallerwebi.presentacion.CajaDto;

@Service
public class ServicioCajaImpl implements ServicioCaja{
    
    @Override
    public List<CajaDto> obtener() {
        throw new UnsupportedOperationException("Unimplemented method 'obtener'");
    }
    
}
