package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ObraDto;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("servicioCarrito")
public class ServicioCarritoImpl implements ServicioCarrito {

    private Carrito carrito = new Carrito();

    public void agregar(Obra obra) {
        carrito.agregarObra(obra);
    }

    public void eliminar(Long idObra) {
        carrito.eliminarObra(idObra);
    }

    public void vaciar() {
        carrito.vaciarCarrito();
    }

    public Iterable<Obra> obtenerItems() {
        return carrito.getObras();
    }
}
