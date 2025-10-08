package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioCarrito")
public class ServicioCarritoImpl implements ServicioCarrito {

    @Override
    public Integer getCantidadTotal() {
        return 0;
    }
    
}