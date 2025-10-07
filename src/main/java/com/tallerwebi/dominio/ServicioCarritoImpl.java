package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service("servicioCarrito")
public class ServicioCarritoImpl implements ServicioCarrito {

    private final Carrito carrito = new Carrito();

    public void agregar(Obra obra) {
        carrito.agregarObra(obra);
    }

    public void eliminar(Long idObra) {
        carrito.eliminarObra(idObra);
    }

    public void vaciar() {
        carrito.vaciarCarrito();
    }

    public Set<Obra> obtenerItems() {
        return carrito.getObras();
    }
}
