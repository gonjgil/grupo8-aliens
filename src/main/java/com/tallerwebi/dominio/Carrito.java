package com.tallerwebi.dominio;

import java.util.HashSet;
import java.util.Set;

public class Carrito {

    private Set<Obra> obras;

    public Carrito() {
        this.obras = new HashSet<>();
    }

    public void agregarObra(Obra obra) {
        obras.add(obra);
    }

    public void eliminarObra(Long idObra) {
        obras.removeIf(o -> o.getId().equals(idObra));
    }

    public void vaciarCarrito() {
        obras.clear();
    }

    public Set<Obra> getObras() {
        return obras;
    }
}
