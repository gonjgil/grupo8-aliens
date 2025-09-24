package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.enums.Formato;

public class ServicioCompraImpl implements ServicioCompra {

    @Override
    public List<Formato> mostrarFormatos() {
        return List.of(Formato.values());
    }
}
