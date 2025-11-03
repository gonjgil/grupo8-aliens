package com.tallerwebi.dominio.servicioImpl;

import java.util.List;

import com.tallerwebi.dominio.ServicioCompra;
import com.tallerwebi.dominio.enums.Formato;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("compraServicio")
@Transactional
public class ServicioCompraImpl implements ServicioCompra {

    @Override
    public List<Formato> mostrarFormatos() {
        return List.of(Formato.values());
    }
}
